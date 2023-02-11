# Soysa-Aquarium

## general instructions for team members for project setup 

### prerequisities -
install git bash -  [git-Windows](https://github.com/git-for-windows/git/releases/download/v2.39.1.windows.1/Git-2.39.1-64-bit.exe) <br />
configure your Database with these queries - [SQL](https://docs.google.com/document/d/1me-R8GTyIGUQ4DQOmFUtMmjxN7g2ibS6ASZRPdGfM78/edit)

### If you don't have the project yet - follow these steps -> 

Go to the directory that you want to initialize the project.

open CMD by typing cmd in the address bar. Or just open any terminal and go to the project directory.

Then type `git clone <this-project-link>` copy the link from this repository.
> And if you're doing it for the first time it'll redirect to a page to ask for permission. Give access and go back.

### Now If you have the project setup - Then you can use the following commands to add new changes or get the latest changes

Open CMD **inside** the project directory.

*To get the latest version* 

`git pull`

*To add your changes* 

> it's always a good practice to do a `git fetch` and `git pull` before pushing

`git add .`

`git commit -m "a-comment-describing-what-you-did"`

> and if you try to commit for the first time you may asked to set your email and name (just a one time process). copy the 2 prompted queries and set your email and name one by one. then commit again.

`git push`

We recommend all contributers to double check your chnges before pushing to avoid conflicts 😇 Happy coding 👍


