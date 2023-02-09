# git notes

```
git
	config
		git config --global user.name "guiwuu"
		git config --global user.email "guiwuu@gmail.com"
		git config --global color.ui "always"
		git config core.filemode false
			disable git diff old mode 10755 new mode 10644 on Windows
		git config --global core.editor /usr/bin/vim
	repo
		git init
		git clone
		git clone --depth=16
	diff
		git diff --word-diff
		git apply ~/Desktop/525010c..6c996d5.diff
	commits
		git status
		git commit
		git pull origin
		git push -u origin
		git add
		git log
			git log HEAD -1
			git log --oneline
		git commit --amend
		git cherry-pick d467740 de906d4
	cherry-pick
		git cherry-pick d467740 de906d4
		git cherry-pick --continue
		git cherry-pick --abort
		git cherry-pick -m 1 <hash>
	revert
		git revert HEAD
		git reset --hard b7057a9
		git revert HEAD -m 1
	branch
		branch
			git branch hotfix
			git checkout hotfix
			git push origin hotfix
			git checkout ea9390aa -b maiinline
		delete
			git branch -d hotfix
			git push origin :hotfix
			git branch -d -r origin/hotfix
		upstream
			git branch --unset-upstream
			git branch --set-upstream master origin/master
	merge
		git merge master
		vi path_to_conflict_file
		git add/rm path_to_conflict_file
		git commit
	tag
		tag
			git tag -a v1.0 [1b2e1d63ff] -m "msg"
			git push origin v1.0
			git push origin --tags
		delete
			git tag -d v1.0
			git push origin :refs/tags/v1.0
			git push origin :v1.0
		git fetch --tag
	stash
		git stash
		git stash pop
	remote
		git remote -v
		git remote set-url origin git@github.com:foo/bar.git 
		设置master的remote
			git remote add origin  git@github.com:foo/bar.git
			git branch --set-upstream-to=origin/master master
		git remote remove origin
		git fetch {remote}
			git checkout -b fork_branch fork/<branch>
	rebase
		git reabse -i HEAD~2
			merge recent two commits
		git rebase mainline
			rebase with mainline, like merge
```