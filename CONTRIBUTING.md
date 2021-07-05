Most technical decisions are decided through the "RFC" (Request for Comments) process. Small changes, such as minor grammatical edits to the specification, do not need to need to follow the RFC process. However, if one intends to make a substantive change to the USUBotUniverse specification , the following process should be adhered to:


1. Ideally have an informal discussion of the topic on the [USUBotUniverse Subreddit](https://www.reddit.com/r/USUBotUniverse/) with the "Change discussion"-flair in order to gauge basic viability. As a rule of thumb, receiving encouraging feedback from long-standing community members is a good indication that the RFC is worth pursuing.

2. Write up a formal proposal, including requested changes to the current specification, as a pull request on GitHub
A core team member will be reviewing your pull request of this RFC.

3. Work to build broad support from the community. Encouraging people to comment, show support, show dissent, etc. Ultimately the level of community support for a change will decide its fate.

4. RFCs rarely go through this process unchanged, especially as alternatives and drawbacks are discovered. You can make edits to the RFC to clarify or change the design, but make changes as new commits to the pull request, and leave a comment on the pull request explaining your changes. Specifically, do not squash or rebase commits after they are visible on the pull request.

5. When it appears that a discussion is no longer progressing in a constructive way, or a general consensus has been reached, the assinged core team member will make an official summary and call for votes in a reddit post with the "Vote change"-flair. This call shall be advertised broadly and will last ten calendar days. Any interested member may vote via +1/-1.

6. After the voting process is complete the core group shall decide to officially approve this RFC. It is expected that barring extreme circumstances this is a rubber stamp of the voting process. An example of an exceptional case would be if representatives for every USUBotUniverse implementation vote against the feature for feasibility reasons.

7. When an RFC is approved it will become part of the current draft version of the specification. This will allow time for implementers to verify feasibility and cutting edge users to get used to the new syntax. The change will then be added to the changelog with credits for all community members with major impact on the change.
 
8. In order to prevent untested features from entering into an official specification version at least one USUBotUniverse implementation must support a feature before it’s allowed to be merged into the current draft version.
