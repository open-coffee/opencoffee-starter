#!/bin/sh

# Helper variables
_REPO_ROOT=$(git rev-parse --show-toplevel)

# Helper functions
error() {
  >&2 echo "ERROR:" "$@"
  exit 1
}

info() {
  >&2 echo "INFO:" "$@"
}

# Get current version as specified in the project configuration
current_version() {
  grep -e '^version=.*$' "${_REPO_ROOT}/gradle.properties" | cut -d '=' -f 2
}

## Different checks (versions and git)
# Check if the supplied version is a development version
is_development_version() {
  _version=$1
  echo "${_version}" | grep -q -e '-SNAPSHOT$'
}

# Check if the supplied version is a (pre-)release version
is_release_version() {
  _version=$1
  echo "${_version}" | grep -v -q -e '-SNAPSHOT$'
}

# Check if the supplied version is a REAL pre-release version
is_prerelease_version() {
  _version=$1
  echo "${_version}" | grep -E -q -e '\.(RC|M)[[:digit:]]+$'
}
