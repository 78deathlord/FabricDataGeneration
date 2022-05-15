package me.wanderingsoul.fabricdatageneration.data.json;

public interface JsonProperty {
    String getName();
    String serialize();

    class IntProperty implements JsonProperty {
        private final String name;
        private final Integer value;

        public IntProperty(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Integer getValue() {
            return value;
        }
    }

    class ByteProperty implements JsonProperty {
        private final String name;
        private final Byte value;

        public ByteProperty(String name, Byte value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Byte getValue() {
            return value;
        }
    }

    class ShortProperty implements JsonProperty {
        private final String name;
        private final Short value;

        public ShortProperty(String name, Short value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Short getValue() {
            return value;
        }
    }

    class LongProperty implements JsonProperty {
        private final String name;
        private final Long value;

        public LongProperty(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Long getValue() {
            return value;
        }
    }

    class FloatProperty implements JsonProperty {
        private final String name;
        private final Float value;

        public FloatProperty(String name, Float value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Float getValue() {
            return value;
        }
    }

    class DoubleProperty implements JsonProperty {
        private final String name;
        private final Double value;

        public DoubleProperty(String name, Double value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Double getValue() {
            return value;
        }
    }

    class BooleanProperty implements JsonProperty {
        private final String name;
        private final Boolean value;

        public BooleanProperty(String name, Boolean value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value;
        }

        public Boolean getValue() {
            return value;
        }
    }

    class CharProperty implements JsonProperty {
        private final String name;
        private final Character value;

        public CharProperty(String name, Character value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":\""+value+"\"";
        }

        @Override
        public String getName() {
            return name;
        }

        public Character getValue() {
            return value;
        }
    }

    class StringProperty implements JsonProperty {
        private final String name;
        private final String value;

        public StringProperty(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":\""+value+"\"";
        }

        public String getValue() {
            return value;
        }
    }

    class ObjectProperty implements JsonProperty {
        private final String name;
        private final JsonObject value;

        public ObjectProperty(String name, JsonObject value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value.serialize();
        }

        public JsonObject getValue() {
            return value;
        }
    }

    class ArrayProperty implements JsonProperty {
        private final String name;
        private final JsonArray value;

        public ArrayProperty(String name, JsonArray value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String serialize() {
            return "\""+name+"\":"+value.serialize();
        }

        public JsonArray getValue() {
            return value;
        }
    }
}
