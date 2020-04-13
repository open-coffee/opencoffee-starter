#!/bin/sh

set -o errexit
set -o pipefail

for old_tag in $(git tag -l | grep '^coffeenet-starter-'); do
  new_tag=$(echo "${old_tag}" | sed -E -e 's/^coffeenet-starter-(.*)/v\1/')
  echo "Fixing up tag: ${old_tag} -> ${new_tag}"
  tag_type=$(git cat-file -t "${old_tag}")
  if [ $tag_type = "tag" ]; then
    echo "Annotated tag ${old_tag}, rewriting tag object"
    new_hash=$(git cat-file -p "${old_tag}" | sed -e "s/^tag .*/tag ${new_tag}/" | git mktag)
    git update-ref "refs/tags/${new_tag}" $new_hash
  elif [ $tag_type = "commit" ]; then
    git tag -f "${new_tag}" "${old_tag}"
  else
    echo "ref type for ${old_tag} unknown, dont know what to do"
  fi
done
