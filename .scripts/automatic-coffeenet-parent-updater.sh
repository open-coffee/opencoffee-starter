#!/bin/bash

# $1 = private token
if [ 1 -eq $# ]; then

  # global
  token=$1
  gitUserName="CoffeeNet Release Dude"
  gitUserEmail="coffeenet@Tobsch.org"

  starterParentArtifactId="coffeenet-starter-parent"
  organizationFrom="coffeenetrelease"
  branch="update-to-latest-coffeenet-parent-version"
  projectsDir="/tmp/merge-requests"

  declare -a projects=("coffeenet/coffeenet-auth" "coffeenet/coffeenet-discovery" "coffeenet/coffeenet-config-server")

  for projectInformation in "${projects[@]}"
  do

    project=$(cut -d "/" -f 2 <<< "${projectInformation}")
    organizationTo=$(cut -d "/" -f 1 <<< "${projectInformation}")

    echo -e "\n\n# --------------------------------"
    echo "# ${projectInformation}"
    echo -e "# --------------------------------\n"

    echo "> Trying to fork ${organizationTo}/${project}"
    forkHttpStatus=$(/usr/bin/curl \
      --location \
      --post301 \
      --write-out '%{http_code}' \
      --silent \
      --output /dev/null \
      -X POST \
      -H "Authorization: token ${token}" \
      -H "Accept: application/vnd.github.v3+json" \
      "https://api.github.com/repos/${organizationTo}/${project}/forks")
    if [[ "$forkHttpStatus" == "202" ]]; then
      echo -e "  -> Forked ${organizationTo}/${project}\n"
    else
      echo -e "  -> Could not fork ${organizationTo}/${project} ($forkHttpStatus)"
      continue
    fi

    if [[ ! -e ${projectsDir} ]]; then
      mkdir -p ${projectsDir}
      echo -e "> Created ${projectsDir}\n"
    fi

    if [[ ! -e ${projectsDir}/${project} ]]; then
      /usr/bin/git clone https://github.com/${organizationFrom}/${project} ${projectsDir}/${project} &> /dev/null
      echo -e "> Cloned 'https://github.com/${organizationFrom}/${project}' to '${project}sDir/${project}'\n"
    fi

    /usr/bin/git -C ${projectsDir}/${project} config user.name "${gitUserName}" &> /dev/null
    /usr/bin/git -C ${projectsDir}/${project} config user.email "${gitUserEmail}" &> /dev/null
    echo -e "> Set git user to '${gitUserName}' with email '${gitUserEmail}'\n"

    if [ ! -f ${projectsDir}/${project}/pom.xml ]; then
        echo -e ">>>> Skipping '${projectInformation}', because it is not a maven project\n"
        continue
    fi

    if ! /usr/bin/git -C ${projectsDir}/${project} ls-remote coffeenet &>/dev/null; then
      /usr/bin/git -C ${projectsDir}/${project} remote add coffeenet https://${token}@github.com/${organizationTo}/${project}.git &> /dev/null
      echo -e "> Add coffeenet remote (${organizationTo}/${project})\n"
    fi

    /usr/bin/git -C ${projectsDir}/${project} remote set-url origin https://${token}@github.com/${organizationFrom}/${project}.git &> /dev/null
    echo -e "> Changed the origin remote url with access token\n"

    CURRENT_BRANCH=$(/usr/bin/git -C ${projectsDir}/${project} rev-parse --abbrev-ref HEAD)
    if [[ "$CURRENT_BRANCH" != "master" ]]; then
      /usr/bin/git -C ${projectsDir}/${project} checkout master &> /dev/null
      echo -e "> Changed to master branch\n"
    fi

    /usr/bin/git -C ${projectsDir}/${project} pull --rebase coffeenet master --prune &> /dev/null
    echo -e "> Updated master branch and removed local orphan branches\n"

    if /usr/bin/git -C ${projectsDir}/${project} status; then
      /usr/bin/git -C ${projectsDir}/${project} push origin master &> /dev/null
      echo -e "> Updated ${organizationFrom}/${project} master with the master from ${organizationTo}/${project}\n"
    fi

    parentArtifactId=`/usr/bin/mvn -f ${projectsDir}/${project}/pom.xml org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.parent.artifactId | grep -Ev '(^\[|Download\w+:)'`
    if [ "${parentArtifactId}" != "${starterParentArtifactId}" ]; then
        echo -e ">>>> Skipping '${projectInformation}', because not using '${starterParentArtifactId}' ('${parentArtifactId}')\n"
        continue
    fi

    # updating the parent version
    echo -e "> Trying to update the parent...\n"
    /usr/bin/mvn -f ${projectsDir}/${project}/pom.xml versions:update-parent -q

    if /usr/bin/git -C ${projectsDir}/${project} status | grep -q pom.xml; then

      coffeeNetParentVersion=`/usr/bin/mvn -f ${projectsDir}/${project}/pom.xml org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.parent.version | grep -Ev '(^\[|Download\w+:)'`
      currentParentVersion=${coffeeNetParentVersion// }
      echo -e "> Updated CoffeeNet parent version of '${projectInformation}' to '${coffeeNetParentVersion}'\n"

      if [ "${coffeeNetParentVersion}" == "@" ]; then
          echo -e ">>>> Skipping '${projectInformation}', because wrong parent version '${coffeeNetParentVersion}'\n"
          continue
      fi

      updateBranch=${branch}-${coffeeNetParentVersion}
      echo -e "> Branch for the pull request base is ${updateBranch}\n";

      /usr/bin/git -C ${projectsDir}/${project} checkout -b ${updateBranch} &> /dev/null
      echo -e "> Changed branch to '${updateBranch}'\n"

      rm ${projectsDir}/${project}/pom.xml.versionsBackup
      echo -e "> Removed ${project}sDir/${project}/pom.xml.versionsBackup\n"

      /usr/bin/git -C ${projectsDir}/${project} commit -am "update to latest CoffeeNet parent version ${coffeeNetParentVersion}" &> /dev/null
      echo -e "> Created commit to update to latest CoffeeNet parent version ${coffeeNetParentVersion}\n"

      /usr/bin/git -C ${projectsDir}/${project} push -u origin ${updateBranch} -f &> /dev/null
      echo -e "> Pushed CoffeeNet parent update to ${organizationFrom}/${updateBranch}\n"

      pull_request_body="{
        \"title\": \"Update to latest CoffeeNet parent version ${coffeeNetParentVersion}\",
        \"body\": \"New CoffeeNet parent version ${coffeeNetParentVersion} was released! Please check https://github.com/coffeenet/coffeenet-starter/blob/master/CHANGELOG.md for more information\",
        \"head\": \"${organizationFrom}:${updateBranch}\",
        \"base\": \"master\",
        \"maintainer_can_modify\": true
      }"

      echo "> Trying to open a pull request for ${organizationFrom}/${project} with ${updateBranch}"
      pullRequestHttpStatus=$(/usr/bin/curl \
        --location \
        --post301 \
        --write-out '%{http_code}' \
        --silent \
        --output /dev/null \
        -X POST \
        -H "Authorization: token ${token}" \
        -H "Accept: application/vnd.github.v3+json" \
        -H "Content-Type: application/json" \
        "https://api.github.com/repos/${organizationTo}/${project}/pulls" \
        -d "$pull_request_body")
      if [[ "$pullRequestHttpStatus" == "201" ]]; then
        echo -e "    -> Opened pull request for ${organizationFrom}/${project} on ${updateBranch}\n"
      else
        echo -e "  X -> Could not open pull request for ${organizationTo}/${project} on ${updateBranch} ($pullRequestHttpStatus)\n"
      fi

      /usr/bin/git -C ${projectsDir}/${project} checkout master &> /dev/null
      /usr/bin/git -C ${projectsDir}/${project} branch -D ${updateBranch} &> /dev/null
      echo -e "> Deleted local branch '${updateBranch}' on '${organizationFrom}/${project}'\n"

    else
      echo -e ">>>> Nothing to upgrade - no new CoffeeNet parent available!\n\n"
    fi
  done
else
  echo "this scripts expects 1 parameter:"
  echo "  1: private token of the user that shall be used to create the pull request"
  exit -1
fi
