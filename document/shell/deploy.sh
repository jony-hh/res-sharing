#!/usr/bin/sh

# 忽略错误
set -e

# 删除文件需要根据实际打包的目录进行删除
rimraf docs/.vitepress/dist

# 构建
npm run docs:build

# 进入待发布的目录
cd docs/.vitepress/dist

# 提示用户输入提交信息
read -p "Enter commit message: " commit_message

git init
git add -A
git commit -m "$commit_message"

# 如果部署到 https://<USERNAME>.github.io
git branch -M main
git remote add origin git@github.com:jony-hh/jony-docs.git
git push -u origin main

echo "Changes committed and pushed to the remote repository."


