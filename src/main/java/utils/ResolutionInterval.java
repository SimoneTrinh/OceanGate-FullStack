package utils;

public class ResolutionInterval {
    private final String value;
    private final String label;

    public ResolutionInterval(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static String[] getAllLabels(ResolutionInterval[] items) {
        String[] labels = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            labels[i] = items[i].getLabel();
        }
        return labels;
    }

    public static String getValueByLabel(ResolutionInterval[] items, String label) {
        for (ResolutionInterval item : items) {
            if (item.getLabel().equalsIgnoreCase(label)) {
                return item.getValue();
            }
        }
        return null;
    }
}
