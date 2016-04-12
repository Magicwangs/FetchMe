rem echo %~dp0
@echo off
set currentDir=%~dp0
cd /d "%currentDir%"
git status
git add .
git commit -m "update%time%"
git push