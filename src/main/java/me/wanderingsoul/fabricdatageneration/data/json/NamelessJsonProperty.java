package me.wanderingsoul.fabricdatageneration.data.json;

public interface NamelessJsonProperty {
    String serialize();

    class IntProperty implements NamelessJsonProperty {
        private final Integer value;

        public IntProperty(Integer value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Integer getValue() {
            return value;
        }
    }

    class ByteProperty implements NamelessJsonProperty {
        private final Byte value;

        public ByteProperty(Byte value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Byte getValue() {
            return value;
        }
    }

    class ShortProperty implements NamelessJsonProperty {
        private final Short value;

        public ShortProperty(Short value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Short getValue() {
            return value;
        }
    }

    class LongProperty implements NamelessJsonProperty {
        private final Long value;

        public LongProperty(Long value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Long getValue() {
            return value;
        }
    }

    class FloatProperty implements NamelessJsonProperty {
        private final Float value;

        public FloatProperty(Float value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Float getValue() {
            return value;
        }
    }

    class DoubleProperty implements NamelessJsonProperty {
        private final Double value;

        public DoubleProperty(Double value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Double getValue() {
            return value;
        }
    }

    class BooleanProperty implements NamelessJsonProperty {
        private final Boolean value;

        public BooleanProperty(Boolean value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value+"";
        }

        public Boolean getValue() {
            return value;
        }
    }

    class CharProperty implements NamelessJsonProperty {
        private final Character value;

        public CharProperty(Character value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return "\""+value+"\"";
        }

        public Character getValue() {
            return value;
        }
    }

    class StringProperty implements NamelessJsonProperty {
        private final String value;

        public StringProperty(String value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return "\""+value+"\"";
        }

        public String getValue() {
            return value;
        }
    }

    class ObjectProperty implements NamelessJsonProperty {
        private final JsonObject value;

        public ObjectProperty(JsonObject value) {
            this.value = value;
        }

        @Override
        public String serialize() {
            return value.serialize();
        }

        public JsonObject getValue() {
            return value;
        }
    }
}
