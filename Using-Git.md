# Using Git

#### What is Git?

Git is a Version Control System (VCS) which is used for tracking changes in the source code for a project. It is especially 
useful for collaborative group projects where multiple people are working at the same time.

## Basic Linux Commands

In order to use Git, you will need to have knowledge of some basic linux commands which will allow you to navigate through 
your terminal. If you are using windows, you should use Git Bash (which comes preinstalled with git). If you are on a mac, 
you can use your computer's Terminal.

#### Finding out where you are

The `pwd` command (which stands for print working directory) allows you to see the exact path for where you are in your file 
directories. The output will look something like the following `/Users/darshankrishnaswamy`. 

#### Listing files

The `ls` command allows you to print a list of all files and folders in your current directory. The output will be a long 
list containing the names of these files and folders. You can also run different versions of this command such as `ls -a`,
which prints all files (including hidden files whose names start with a `.`), `ls -l` which prints the names of the files and 
folders as well as information about them, and `ls -R` which recursively lists all files and folders as well as files 
and folders within each folder.

#### Changing your current directory

The `cd` command allows you to change where you are. Running `cd` on its own will take you to your home directory. Running
`cd foldername` allows you to change your directory into the folder named "foldername". Running `cd ..` takes you back one 
folder (for example, if your current path is `/users/darshankrishnaswamy/documents/somefolder`, then running `cd ..` will 
take you to `/users/darshankrishnaswamy/documents`.

## Git Commands

Note that all git commands should be run from the project directory (in this case, that would be the CommUnity folder on your
computer once you clone the repository).

The most basic git commands are `git add`, `git commit`, `git push`, and `git pull`.

`git add` allows you to add files to version control. If you make a new file, the file will not be added to the repository 
until you run `git add filename`. Additionally, if you modify a file, you have to run `git add filename` in order to stage 
your changes to be added to version control. To make things simple, as long as you have a `.gitignore` file created (which 
Android Studio does by default), you can run `git add .`, which adds all new or edited files to git.

`git commit` allows you to save any new files or changes that you have made (this must be run after you have added all the 
files that you have changed). It is generally run as `git commit -m "message"`, where message is your commit message (a 
short description of what you have added or changed). If you do not specify a message, a pop up window with a text editor
(which could be notepad++, vim, atom, etc. depending on what you selected when you downloaded git) will appear and ask you 
for your commit message. Note: You should ALWAYS make sure that your code compiles and runs without any errors before 
committing (and especially before pushing).

`git push` allows you to send your changes to github, where it can be viewed checked out by other members of your team. 
You will generally run this as `git push origin branchname`, where branchname is the name of the branch you are working on 
(more information on branches is below). Note: Running `git push` on its own pushes to "upstream", which is the `master`
branch by default. In general, you don't want to push directly to master. Rather, you want to push to a branch, then open
a pull request to merge your changes into `master` (this helps to avoid merge conflicts and other problems). 

`git pull` allows you to "pull" any changes from the github repository onto your local clone of the repository. When someone
makes a change to the repository, in order to access these changes, you will have to run `git pull origin branchname` where 
branchname is the name of the branch that you want to pull changes from (this is generally the `master` branch). If you have 
unstaged changes when you try to pull, git will often try to block you from pulling until you have committed your changes.

## Branches and Pull Requests

When using git, you often don't want all members of the team pushing directly to the `master` branch, as this will cause 
frequent merge conflicts (when two or more people try to edit the same content in the same file and git does not know which
to accept). Instead, you should check out a branch, push your changes to the branch, and then merge this branch with 
`master`.

#### Creating a branch

To create a branch, you should run `git checkout -b branchname`. If you want to check out a branch that has already been 
created, simply run `git checkout branchname`. To see which branch you are currently working on, run `git branch`. 

#### Creating a Pull Request

Once you have pushed your changes to a branch (see above for information on how to do this), you should open a pull request 
to merge your changes into the `master` branch. Once your changes have been pushed to the branch, open the repository on 
github. Click on where it says `branch: master` and select the branch that you pushed your changes to. Then click on the 
`pull request` button (which will be next to a button that says `compare`). Set an appropriate title for the pull request,
then click on create pull request.

#### Merging your pull request

If you want someone to review your pull request before merging it (because you want someone to check over your code to make
sure everything is good before it gets merged), stop here and notify the person who you want to review your code. They can
check over everything and then merge it into `master`. If you are sure that your code will work properly (if you only made a 
minor change and are sure that it won't cause any problems), then you can click `merge pull request`.

## Using git status

There is a terminal command called `git status` which allows you to see the status of the files in your directory before you
add or commit. When you run the command, if it says that "Your branch is up to date with 'origin/branchname'.
nothing to commit, working tree clean", it means that you have not made any changes that haven't been committed. If files 
appear in red, this means that they have not been added to git (this excludes files that are explicitly ignored in the .gitignore 
file). If files appear in green, this means they have been added, but not committed. Note: files that have been committed but not
pushed are not displayed by `git status`.

