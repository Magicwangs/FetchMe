rem echo %~dp0
@echo off
set currentDir=%~dp0
cd /d "%currentDir%"
git rm -r --cached .
git status
git add .
git commit -m "reset%time%"
git push