#!/bin/bash

# $1 = private token
if [ 1 -eq $# ]; then

  # global
  token=$1
  branch="update-to-latest-coffeenet-parent-version"
  projectsDir="/tmp/coffeenet-merge-requests"
  organizationTo="coffeenet"
  organizationFrom="coffeenetrelease"

  declare -a projects=("coffeenet-auth" "coffeenet-discovery" "coffeenet-config-server")

  for project in "${projects[@]}"
  do
    echo "# $project"
    echo -e "# --------------------------------\n"

    echo "> Trying to fork $organizationTo/$project"
    forkHttpStatus=$(/usr/bin/curl \
      --write-out '%{http_code}' \
      --silent \
      --output /dev/null \
      -X POST \
      -H "Authorization: token $token" \
      -H "Accept: application/vnd.github.v3+json" \
      "https://api.github.com/repos/$organizationTo/$project/forks")
    if [[ "$forkHttpStatus" == "202" ]]; then
      echo -e "\n> Forked $organizationTo/$project\n"
    else
      echo -e "\n> Could not fork $organizationTo/$project ($forkHttpStatus) -> exiting"
      exit -1
    fi

    if [[ ! -e ${projectsDir} ]]; then
      mkdir -p ${projectsDir}
      echo -e "> Created $projectsDir\n"
    fi

    if [[ ! -e ${projectsDir}/${project} ]]; then
      /usr/bin/git clone https://github.com/${organizationFrom}/${project} ${projectsDir}/${project}
      echo -e "> Cloned 'https://github.com/$organizationFrom/$project' to '$projectsDir/$project'\n"
    fi

    if ! /usr/bin/git -C ${projectsDir}/${project} ls-remote coffeenet &>/dev/null; then
      /usr/bin/git -C ${projectsDir}/${project} remote add coffeenet https://${token}@github.com/${organizationTo}/${project}.git
     echo -e "> Add coffeenet remote ($organizationTo/$project)\n"
    fi

    /usr/bin/git -C ${projectsDir}/${project} remote set-url origin https://${token}@github.com/${organizationFrom}/${project}.git
    echo -e "> Changed the origin remote url with access token\n"

    CURRENT_BRANCH=$(/usr/bin/git -C ${projectsDir}/${project} rev-parse --abbrev-ref HEAD)
    if [[ "$CURRENT_BRANCH" != "master" ]]; then
      /usr/bin/git -C ${projectsDir}/${project} checkout master
      echo -e "> Changed to master branch\n"
    fi

    /usr/bin/git -C ${projectsDir}/${project} fetch -p
    echo -e "> Removed local orphan branches\n"

    /usr/bin/git -C ${projectsDir}/${project} pull --rebase coffeenet master
    echo -e "> Rebased '$projectsDir/$project' to latest coffeenet master\n"

    if /usr/bin/git -C ${projectsDir}/${project} status; then
      /usr/bin/git -C ${projectsDir}/${project} push origin master
      echo -e "> Updated ${organizationFrom}/${project} master with the master from ${organizationTo}/${project}\n"
    fi

    # updating the parent version
    /usr/bin/mvn -f ${projectsDir}/${project}/pom.xml versions:update-parent &>/dev/null

    if /usr/bin/git -C ${projectsDir}/${project} status | grep -q pom.xml; then

      coffeeNetParentVersion=`/usr/bin/mvn -f ${projectsDir}/${project}/pom.xml help:evaluate -Dexpression=project.parent.version | grep -e '^[^\[]'`
      echo -e "> Updated CoffeeNet parent version of '$projectsDir/$project' to $coffeeNetParentVersion\n"

      updateBranch=${branch}-${coffeeNetParentVersion}
      echo "updateBranch= $updateBranch";

      /usr/bin/git -C ${projectsDir}/${project} checkout -b ${updateBranch}
      echo -e "> Changed branch to '$updateBranch'\n"

      rm ${projectsDir}/${project}/pom.xml.versionsBackup
      echo -e "> Removed $projectsDir/$project/pom.xml.versionsBackup\n"

      /usr/bin/git -C ${projectsDir}/${project} commit -am "update to latest CoffeeNet parent version $coffeeNetParentVersion"
      echo -e "> Created commit to update to latest CoffeeNet parent version $coffeeNetParentVersion\n"

      /usr/bin/git -C ${projectsDir}/${project} push -u origin ${updateBranch}
      echo -e "> Pushed CoffeeNet parent update to $organizationFrom/$updateBranch\n"

      pull_request_body="{
        \"title\": \"Update to latest CoffeeNet parent version $coffeeNetParentVersion\",
        \"body\": \"New CoffeeNet parent version $coffeeNetParentVersion was released! Please check
          https://github.com/coffeenet/coffeenet-starter/blob/master/CHANGELOG.md for more information\",
        \"head\": \"$organizationFrom:$updateBranch\",
        \"base\": \"master\",
        \"maintainer_can_modify\": true
      }"
      echo -e " \n\n$pull_request_body\n\n"

      echo "> Trying to open a pull request for $organizationFrom/$project with $updateBranch"
      pullRequestHttpStatus=$(/usr/bin/curl \
        --write-out '%{http_code}' \
        --silent \
        --output /dev/null \
        -X POST \
        -H "Authorization: token $token" \
        -H "Accept: application/vnd.github.v3+json" \
        -H "Content-Type: application/json" \
        "https://api.github.com/repos/$organizationTo/$project/pulls" \
        -d "$pull_request_body")
      if [[ "$pullRequestHttpStatus" == "202" ]]; then
        echo -e "> Opened pull request for $organizationFrom/$project on $updateBranch\n"
      else
        echo -e "\n> Could not open pull request for $organizationTo/$project on $updateBranch ($pullRequestHttpStatus) -> exiting"
        exit -1
      fi

      /usr/bin/git -C ${projectsDir}/${project} checkout master
      /usr/bin/git -C ${projectsDir}/${project} branch -D ${updateBranch}
      echo -e "> Deleted local branch '$updateBranch' on '$organizationFrom/$project'\n"

    else
      echo -e ">>>> Nothing to upgrade - no new CoffeeNet parent available!\n\n"
    fi
  done
else
  echo "this scripts expects 1 parameter:"
  echo "  1: private token of the user that shall be used to create the pull request"
  exit -1
fi
