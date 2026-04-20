@echo off
setlocal

set "JAVA_PATH=%~1"
if "%JAVA_PATH%"=="" set "JAVA_PATH=java"

set "REPO_ROOT=%~dp0.."
cd /d "%REPO_ROOT%"

echo Compiling project...
call mvn -q compile
if errorlevel 1 exit /b 1

> "target\quit-input.txt" echo quit
call :run_case quit "Welcome to Sudoku!" "Here is your puzzle:" "Goodbye!"
if errorlevel 1 exit /b 1

(
  echo Z9 4
  echo quit
) > "target\invalid-input.txt"
call :run_case invalid "Invalid command." "Current grid:" "Goodbye!"
if errorlevel 1 exit /b 1

(
  echo hint
  echo quit
) > "target\hint-input.txt"
call :run_case hint "Hint: Cell " "Current grid:" "Goodbye!"
if errorlevel 1 exit /b 1

(
  echo check
  echo quit
) > "target\check-input.txt"
call :run_case check "No rule violations detected." "Current grid:" "Goodbye!"
if errorlevel 1 exit /b 1

echo Smoke tests completed.
exit /b 0

:run_case
set "CASE_NAME=%~1"
set "EXPECT1=%~2"
set "EXPECT2=%~3"
set "EXPECT3=%~4"

"%JAVA_PATH%" -cp target\classes com.ankit.sudoku.SudokuApp < "target\%CASE_NAME%-input.txt" > "target\%CASE_NAME%-output.txt"
if errorlevel 1 (
  echo Smoke test %CASE_NAME% failed: process exited with error.
  type "target\%CASE_NAME%-output.txt"
  exit /b 1
)

findstr /c:"%EXPECT1%" "target\%CASE_NAME%-output.txt" >nul || goto :case_failed
findstr /c:"%EXPECT2%" "target\%CASE_NAME%-output.txt" >nul || goto :case_failed
findstr /c:"%EXPECT3%" "target\%CASE_NAME%-output.txt" >nul || goto :case_failed

echo Passed: %CASE_NAME%
exit /b 0

:case_failed
echo Smoke test %CASE_NAME% failed.
type "target\%CASE_NAME%-output.txt"
exit /b 1
