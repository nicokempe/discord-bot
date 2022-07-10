# Contributing to the Discord Bot Open Source Project

## Table of Contents
* [Code of Conduct](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#request-features)
* [Contributing](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#contributing)
  * [Reporting bugs](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#reporting-bugs)
  * [Request features](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#request-features)
  * [Reporting security gaps](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#reporting-security-gaps)
  * [Code Contributions](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#code-contributions)
* [Styleguides](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#styleguides)
  * [Git Commit Messages](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#git-commit-messages)
  * [Pull Requests](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#pull-requests)
* [Branches](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#branches)
* [Labels](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#labels)
* [Further questions](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/contributing.md#further-questions)

## Code of Conduct
Our Code of Conduct can be found in the GitHub docs directory:
* [Code of Conduct](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/code_of_conduct.md)

## Contributing

### Reporting bugs
Before you open a issue, please check the following points:
* Check the [Discussions](https://github.com/NicoKempeEU/discord-bot/discussions) and our [Wiki](https://github.com/NicoKempeEU/discord-bot/wiki) first to see if your problem has more information available.
* Perform a [cursory search](https://github.com/NicoKempeEU/discord-bot/blob/main/docs/code_of_conduct.md) to see if your problem is already reported. If there is already a issue open leave a comment there instead of opening a new one. 

If your problem still doesn't seem to be solved then create an issue using the "Bug Report" template.

### Request features
You can also access the GitHub Issues to suggest extensions for this project. There is also a form in which you will be guided through the required steps. So please use the [Feature Request issue template](https://github.com/NicoKempeEU/discord-bot/issues/new?assignees=&labels=%3Asparkles%3A+feature+request&template=feature_request.yml) to suggest requests for this project.

### Reporting security gaps
We provide a separate page for reporting critical bugs. In our [Security Policy](https://github.com/NicoKempeEU/discord-bot/security/policy) you will find all the information you need to understand this topic.

### Code Contributions
As long as the code works and is understandable, nothing else should be considered apart from the usual conventions of the respective programming language.

## Styleguides

### Git Commit Messages
We follow a recommendation from [cbeams](https://cbea.ms/author/cbeams/) when creating commit messages. He created a [blog post](https://cbea.ms/git-commit/) in which he explains how to write your commit messages.

In summary, follow his seven rules of a great Git commit message:
* [Separate subject from body with a blank line](https://cbea.ms/git-commit/#separate)
* [Limit the subject line to 50 characters](https://cbea.ms/git-commit/#limit-50)
* [Capitalize the subject line](https://cbea.ms/git-commit/#capitalize)
* [Do not end the subject line with a period](https://cbea.ms/git-commit/#end)
* [Use the imperative mood in the subject line](https://cbea.ms/git-commit/#imperative)
* [Wrap the body at 72 characters](https://cbea.ms/git-commit/#wrap-72)
* [Use the body to explain what and why vs. how](https://cbea.ms/git-commit/#why-not-how)

### Pull Requests
When creating pull requests, you will automatically receive our template. Stick to this format and record the most important information clearly.
> Note: If your pull request is related to an issue, don't forget to link it. 

#### Here is a positive example of creating your pull request:
[![security-policy-pull-request](https://user-images.githubusercontent.com/50241630/177031595-515251db-f73d-45e7-92cb-a3cdb4744132.png)](https://github.com/NicoKempeEU/discord-bot/pull/34)


## Branches
| **Branch name**           | **Prefix** | **Usage**                                                                  | **Example**                                   |
|---------------------------|------------|----------------------------------------------------------------------------|-----------------------------------------------|
| Main Branch               | main       | (Unique Stable) Main Branch (Current development version)                  | There is only one of this branch: main.       |
| Production release Branch | production | (Unique Stable) Production Branch (Automatically deployed content)         | There is only one of this branch: production. |
| Release Branch            | release/   | Completed releases that are named by the time. (YEAR.MONTH.VERSION_NUMBER) | release/2022.7.4                              |
| Feature Branch            | feature/   | The feature branch only comes with major updates that are being worked on. | feature/ux-update                             |
| Bug(fix) Branch           | fix/       | The name should speak for itself: currently fixed bugs.                    | fix/footer-position-fix                       |

## Labels
| **Label**                                                                                                         | **Explanation**                               | **Usage** | **Example**                                   |
|-------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|-----------|-----------------------------------------------|
| [📚 documentation](https://github.com/NicoKempeEU/discord-bot/labels/%F0%9F%93%9A%20documentation)                 | improvements or additions to documentation    | Core Devs | Code Snippet Discussion                       |
| [📝 duplicate](https://github.com/NicoKempeEU/discord-bot/labels/%F0%9F%93%9D%20duplicate)                         | this issue or pull request already exists     | Core Devs | Issue that is already reported                |
| [🔥 bug](https://github.com/NicoKempeEU/discord-bot/labels/%3Afire%3A%20bug)                                       | something isn't working                       | Everyone  | Bug report issue                              |
| [🔥 fix](https://github.com/NicoKempeEU/discord-bot/labels/%3Afire%3A%20fix)                                       | this fixes content                            | Everyone  | Bug fix pull request                          |
| [❎ help wanted](https://github.com/NicoKempeEU/discord-bot/labels/%E2%9D%8E%20help%20wanted)                      | extra attention is needed                     | Core Devs | When developers don't know what to do         |
| [⚠️ invalid](https://github.com/NicoKempeEU/discord-bot/labels/%E2%9A%A0%EF%B8%8F%20invalid)                       | this doesn't seem right                       | Core Devs | When a client bug is reported as software bug |
| [❓ question](https://github.com/NicoKempeEU/discord-bot/labels/%E2%9D%93%20question)                              | further information is requested              | Core Devs | When more information is needed               |
| [✨ enhancement](https://github.com/NicoKempeEU/discord-bot/labels/%3Asparkles%3A%20enhancement)                   | new feature or enhancement                    | Everyone  | Software enhancing pull request               |
| [✨ feature request](https://github.com/NicoKempeEU/discord-bot/labels/%3Asparkles%3A%20feature%20request)         | feature or enhancement request                | Everyone  | Issue that suggests enhancements              |
| [💣 vulnerability report](https://github.com/NicoKempeEU/discord-bot/labels/%F0%9F%92%A3%20vulnerability%20report) | report that is related to the security policy | Everyone  | Security Policy related issue                 |
| [❕ wontfix](https://github.com/NicoKempeEU/discord-bot/labels/%E2%9D%95%20wontfix)                                | this will not be worked on                    | Core Devs | Feature request that has been denied          |

## Further questions
> Note: please don't open issues to ask questions. Use the resources below
* Information and Tutorials can be found in our **[GitHub Wiki](https://github.com/NicoKempeEU/discord-bot/wiki)**.
* To ask questions you can use our **[GitHub Discussions](https://github.com/NicoKempeEU/discord-bot/discussions)**.
