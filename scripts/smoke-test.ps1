param(
    [string]$JavaPath = "java"
)

$ErrorActionPreference = "Stop"
$repoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $repoRoot

$scriptPath = Join-Path $PSScriptRoot "smoke-test.cmd"
& $scriptPath $JavaPath
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
