Reviewer: Lenna Nicholson (u4671448)
Component: Viewer class
Author: Lyu Xiaoguang (u6464313)

Review Comments:

1. Code makes good use of a variety of features of the javafx library (eg flowpane, control features like buttons) in order to
make use of existing code to implement relevant functionality.
2. Code uses appropriate commenting, and documentation of previous strategy and why changed to new one.
3. Program composition splits the functionality of the various methods appropriately. However, in the drawCard method, images to display
the cards are hardcoded, with each option explicitly enumerated. A loop to populate an array based on common characteristics of the images
needed and image names would be more appropriate.
4. This hard coding increases the likelihood of update errors from this function.
5. Java conventions are followed, and the style is consistent throughout.
6. Refresh button deletes previous placement visual, but does not replace with new placement from text field. In order to do so, it would need
to get the text from the text field and use that as a parameter for makePlacement().