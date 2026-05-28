import fs from 'fs';
import path from 'path';

const packagePath = path.resolve('package.json');
const pkg = JSON.parse(fs.readFileSync(packagePath, 'utf8'));

const versionParts = pkg.version.split('.').map(Number);
versionParts[2] += 1; // Increment patch version

if (versionParts[2] >= 100) {
  versionParts[2] = 0;
  versionParts[1] += 1;
}

if (versionParts[1] >= 100) {
  versionParts[1] = 0;
  versionParts[0] += 1;
}

pkg.version = versionParts.join('.');
fs.writeFileSync(packagePath, JSON.stringify(pkg, null, 2));

console.log(`Version auto-updated to: ${pkg.version}`);