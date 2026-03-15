#!/bin/bash
# sync.sh — Pull latest upstream Moonfin updates and rebase custom changes on top
set -e

echo "🔄 Fetching latest from upstream Moonfin..."
git fetch upstream

echo "⏩ Rebasing our changes on top of upstream/main..."
git checkout main
git rebase upstream/main

echo "📤 Pushing to GitHub..."
git push --force-with-lease origin main

echo "✅ Done! Your changes are now on top of the latest upstream Moonfin."
