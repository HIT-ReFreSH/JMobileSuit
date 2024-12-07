#!/bin/bash
# auto_commit.sh - 自动化 Git 提交脚本
# 用于检测当前目录中的变更文件并自动添加和提交

# 检查当前目录是否为 Git 仓库
if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
    echo "Error: 当前目录不是一个 Git 仓库，请检查路径。"
    exit 1
fi

# 检查是否有变更的文件
changed_files=$(git status --porcelain | awk '{print $2}')
if [ -z "$changed_files" ]; then
    echo "没有检测到变更的文件，无需提交。"
    exit 0
fi

# 遍历变更文件并逐一提交
for file in $changed_files; do
    git add "$file"
    git commit -m "Add files via upload: $file"
    echo "已提交文件: $file"
done

echo "所有变更文件已成功提交。"
