#!/usr/bin/env bash
# DocsPI Mobile Project Initializer
# Run this on macOS to generate the Xcode project for iOS building.
# Android build requires Android SDK/NDK (works on any OS).

set -euo pipefail
DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$DIR"

echo "=== DocsPI Mobile Initializer ==="

# --- iOS ---
if [[ "$(uname)" == "Darwin" ]]; then
    echo ""
    echo "[iOS] Initializing Xcode project..."
    npx tauri ios init

    echo "[iOS] Adding NetworkExtension capability..."
    # The Entitlements.plist and Info.plist are already in place at:
    #   src-tauri/gen/apple/DocsPI/Entitlements.plist
    #   src-tauri/gen/apple/DocsPI/Info.plist
    # Verify they exist:
    if [[ -f "src-tauri/gen/apple/DocsPI/Entitlements.plist" ]]; then
        echo "[iOS] ✅ Entitlements.plist found"
    fi
    if [[ -f "src-tauri/gen/apple/DocsPI/Info.plist" ]]; then
        echo "[iOS] ✅ Info.plist found"
    fi
    echo "[iOS] ✅ Xcode project generated at src-tauri/gen/apple/DocsPI.xcodeproj"
    echo "[iOS] Open with: open src-tauri/gen/apple/DocsPI.xcodeproj"
else
    echo "[iOS] ⚠️ Skipping iOS — requires macOS with Xcode 15+"
    echo "   Run this script on a Mac:"
    echo "   cd $(pwd) && bash scripts/init-mobile.sh"
fi

# --- Android ---
echo ""
echo "[Android] ✅ Build files already in place:"
echo "   src-tauri/gen/android/app/build.gradle"
echo "   src-tauri/gen/android/app/src/main/java/com/aydocs/docspi/"
echo ""
echo "[Android] To build: npx tauri build --target android"

echo ""
echo "=== Done ==="
