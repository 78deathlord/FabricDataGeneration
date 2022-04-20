package me.wanderingsoul.fabricdatageneration.data.common;

public enum IngredientType {
    ITEM, TAG;

    @Override
    public String toString() {
        if (this == ITEM) return "item";
        if (this == TAG) return "tag";

        return super.toString();
    }
}
