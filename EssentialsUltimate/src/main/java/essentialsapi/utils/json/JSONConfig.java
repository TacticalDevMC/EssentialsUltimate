package essentialsapi.utils.json;

import com.google.gson.*;

import java.io.*;
import java.text.ParseException;

public class JSONConfig {

    private final File file;
    private JsonObject main;

    public File getFile() {
        return file;
    }

    public JSONConfig(String dir, String fileName) throws IOException, ParseException {
        this(new File(dir, fileName));
    }

    public JSONConfig(File dir, String fileName) throws IOException, ParseException {
        this(new File(dir, fileName));
    }

    public JSONConfig(String fileName) throws IOException, ParseException {
        this(new File(fileName));
    }

    public JSONConfig(File file) throws IOException, ParseException {
        this.file = file;

        if (!file.exists()) {

            if (!file.createNewFile()) {
                throw new RuntimeException("Failed to create file");
            }

            this.main = new JsonObject();
            this.save();
        }

        this.main = (JsonObject) new JsonParser().parse(new FileReader(this.file));
    }

    public void save() throws IOException {
        Writer out = new FileWriter(this.file, false);
        try {
            String input = this.main.toString();
            out.write(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(input)));
        } finally {
            out.close();
        }
    }

    public void expect(String key, JsonObject defVal) throws IOException {
        Object val = this.main.get(key);
        if (val == null) {
            this.main.add(key, defVal);
            this.save();
        }
    }

    public void expect(String key, String defVal) throws IOException {
        Object val = this.main.get(key);
        if (val == null) {
            this.main.addProperty(key, defVal);
            this.save();
        }
    }

    public void expect(String key, int defVal) throws IOException {
        Object val = this.main.get(key);
        if (val == null) {
            this.main.addProperty(key, defVal);
            this.save();
        }
    }

    public void expect(String key, boolean defVal) throws IOException {
        Object val = this.main.get(key);
        if (val == null) {
            this.main.addProperty(key, defVal);
            this.save();
        }
    }


    public void set(String key, String val) {
        this.main.addProperty(key, val);
    }

    public void set(String key, boolean val) {
        this.main.addProperty(key, val);
    }

    public void set(String key, JsonObject val) {
        this.main.add(key, val);
    }

    public void set(String key, int val) {
        this.main.addProperty(key, val);
    }

    public void set(String key, JsonArray value) {
        this.main.add(key, value);
    }

    public String getString(String key) {
        JsonElement val = this.main.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Key " + key + " was not found");
        }
        return val.getAsString();
    }

    public JsonObject getObject(String key) {
        JsonElement val = this.main.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Key " + key + " was not found");
        }
        return val.getAsJsonObject();
    }

    public int getInt(String key) {
        JsonElement val = this.main.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Key " + key + " was not found");
        }
        return val.getAsInt();
    }

    public Double getDouble(String key) {
        JsonElement val = this.main.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Key " + key + " was not found");
        }
        return val.getAsDouble();
    }

    public JsonArray getArray(String key) {
        JsonElement val = this.main.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Key " + key + " was not found");
        }
        return val.getAsJsonArray();
    }

    public boolean getBoolean(String key) {
        JsonElement val = this.main.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Key " + key + " was not found");
        }
        return val.getAsBoolean();
    }
}