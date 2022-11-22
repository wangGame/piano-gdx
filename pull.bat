@echo off
git add ./ >>add.txt
git commit -m "update code" >>commit.txt
git push -origin -u Midi
git pull >> pull.txt