echo %cd%
set currentDir=%cd%
cd "%currentDir%\StudyWeb"
git status
git add .
git commit -m "update%time%"
git push
cd "%currentDir%\GitWeb"
git status
git add .
git commit -m "update%time%"
git push
cd "%currentDir%\Command"
git status
git add .
git commit -m "update%time%"
git push
cd "%currentDir%\Image"
git status
git add .
git commit -m "update%time%"
git push
cd "%currentDir%\Android"
git status
git add .
git commit -m "update%time%"
git push
@echo off
echo %time%
timeout 60 > NUL
echo %time%