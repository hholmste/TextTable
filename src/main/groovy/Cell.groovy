/**
 * A single thing with content and left and right borders.
 */
class Cell {

    public static enum Justification {
        LEFT, RIGHT, CENTER
    }

    String content
    Integer desiredWidth
    Justification justification = Justification.LEFT

    String borderLeft = ""
    String borderRight = ""

    String getFormattedContent() {
        String preformat
        if (desiredWidth >= content.size()) {
            preformat = formatShortContent()
        } else {
            preformat = formatLongContent()
        }
        "$borderLeft$preformat$borderRight"
    }

    private String formatLongContent() {
        "${content.subSequence(0, desiredWidth - 3)}..."
    }

    private String formatShortContent() {
        def requiredPadding = Math.max(0, desiredWidth - content.size())
        Integer leftPadding = 0
        Integer rightPadding = 0
        switch (justification) {
        case Justification.CENTER:
            leftPadding = requiredPadding / 2
            rightPadding = requiredPadding - leftPadding
            break
        case Justification.RIGHT:
            leftPadding = requiredPadding
            rightPadding = 0
            break
        case Justification.LEFT:
            leftPadding = 0
            rightPadding = requiredPadding
            break
        default:
            throw new RuntimeException("What the hell kind of justification is $justification?")
        }

        "${' ' * leftPadding}$content${' ' * rightPadding}"
    }

    public void setDesiredWidth(Integer desiredWidth) {
        if (desiredWidth < 3) {
            this.desiredWidth = 3
        } else {
            this.desiredWidth = desiredWidth;
        }
    }
}
