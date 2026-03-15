#!/bin/bash
# release.sh — Bump version, build APK, tag, and publish a GitHub release
set -e

# Make sure we're on dv-compat
BRANCH=$(git rev-parse --abbrev-ref HEAD)
if [ "$BRANCH" != "dv-compat" ]; then
  echo "❌ You must be on the dv-compat branch to release."
  echo "   Run: git checkout dv-compat"
  exit 1
fi

# Show recent releases
echo "Current releases:"
git tag --sort=-version:refname | head -5
echo ""
read -p "Enter new version (e.g. 1.8.0): " VERSION
TAG="${VERSION}"
TITLE="Moonfin v${VERSION}"

echo ""
read -p "Release notes (one line, or press Enter to open editor): " NOTES
if [ -z "$NOTES" ]; then
  TMPFILE=$(mktemp /tmp/release-notes.XXXXXX.md)
  ${EDITOR:-nano} "$TMPFILE"
  NOTES=$(cat "$TMPFILE")
  rm "$TMPFILE"
fi

echo ""
echo "📝 Bumping version to $VERSION in gradle.properties..."
sed -i '' "s/^moonfin\.version=.*/moonfin.version=${VERSION}/" gradle.properties
git add gradle.properties
git commit -m "Update moonfin.version to ${VERSION}"

echo ""
echo "🔨 Building APK..."
JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home \
ANDROID_HOME=/Users/scott/android-sdk \
./gradlew assembleGithubDebug

APK=$(find app/build/outputs/apk/github/debug -name "*.apk" | head -1)
if [ -z "$APK" ]; then
  echo "❌ APK not found. Build may have failed."
  exit 1
fi

echo "🏷️  Tagging $TAG..."
git tag "$TAG"
git push origin dv-compat
git push origin "$TAG"

echo "🚀 Publishing release..."
gh release create "$TAG" \
  --title "$TITLE" \
  --notes "$NOTES" \
  --latest \
  "$APK"

echo ""
echo "✅ Released $TAG!"
gh release view "$TAG" --web
