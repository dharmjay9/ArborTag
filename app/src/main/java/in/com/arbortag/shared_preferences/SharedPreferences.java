package in.com.arbortag.shared_preferences;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by divum on 16/11/16.
 */

public class SharedPreferences {

    private android.content.SharedPreferences sharedPreferences;

    private SharedPreferences() {
    }

    private SharedPreferences(Context context, String name, int mode) {
        sharedPreferences = context.getSharedPreferences(name, mode);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences(context, context.getApplicationInfo().packageName, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences(Context context, String name, int mode) {
        SharedPreferences sharedPreferences = new SharedPreferences(context, name, mode);
        return sharedPreferences;
    }

    public static SharedPreferences getSharedPreferences(Context context, String name) {
        return getSharedPreferences(context, name, Context.MODE_PRIVATE);
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * Retrieve all values from the preferences.
     * <p/>
     * <p>Note that you <em>must not</em> modify the collection returned
     * by this method, or alter any of its contents.  The consistency of your
     * stored data is not guaranteed if you do.
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     * @throws NullPointerException
     */
    protected Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    /**
     * Retrieve a value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     * @throws ClassCastException
     */
    public <T> T get(String key, T defValue) {
        if (defValue == null || defValue instanceof String) {
            return (T) (String) sharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return (T) (Integer) sharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return (T) (Long) sharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return (T) (Float) sharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Boolean) {
            return (T) (Boolean) sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof IPersistenceObject) {
            return (T) getObject(key, defValue.getClass());
        } else if (defValue instanceof Set) {
            return (T) (Set<String>) sharedPreferences.getStringSet(key, (Set<String>) defValue);
        }
        return null;
    }

    /**
     * Retrieve a value from the preferences.
     *
     * @param key   The name of the preference to retrieve.
     * @param clazz Class
     * @return Returns the preference value if it exists, or an empty object.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     * @throws ClassCastException
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String string = sharedPreferences.getString(key, "");
        return new Gson().fromJson(string, clazz);
    }

    /**
     * Set a {T} value in the preferences once you call this method.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T> SharedPreferences put(String key, T value) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value == null || value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer || value instanceof Short) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof IPersistenceObject) {
            editor.putString(key, new Gson().toJson(value));
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }
        editor.apply();
        return this;
    }

    /**
     * Set a {T} value in the preferences once you call this method.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T extends IPersistenceObject> SharedPreferences putObject(String key, T value) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, new Gson().toJson(value));
        editor.apply();
        return this;
    }

    /**
     * Set a List of value in the preferences once you call this method.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T> SharedPreferences putObject(String key, T value) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, new Gson().toJson(value));
        editor.apply();
        return this;
    }

    /**
     * Delete given object from the List.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T> SharedPreferences deleteElementFromList(String key, T value) {
        if (value == null) {
            return this;
        }
        List<?> list = getList(key, value.getClass());
        Iterator<?> iterator = list.iterator();
        String jsonObject = new Gson().toJson(value);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            String s = new Gson().toJson(next);
            if (s.equals(jsonObject)) {
                iterator.remove();
            }
        }
        putObject(key, list);
        return this;
    }
    /**
     * Delete given object from the List.
     *
     * @param key   The name of the preference to modify.
     * @param index The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T> SharedPreferences deleteElementAt(String key, int index) {
        if (contains(key)) {
            String array = sharedPreferences.getString(key, "");
            List list= new Gson().fromJson(array,new ListParameterizedType(String.class));
            list.remove(index);
            putObject(key, list);
        }
        return this;
    }

    /**
     * Set a List of value in the preferences once you call this method.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T> SharedPreferences appendToList(String key, T value) {
        List list = new ArrayList();
        if (contains(key)) {
            String array = sharedPreferences.getString(key, "");
            list = new Gson().fromJson(array, new TypeToken<List<T>>() {
            }.getType());
        }
        list.add(value);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, new Gson().toJson(list));
        editor.apply();
        return this;
    }

    /**
     * Set a List of value in the preferences once you call this method.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public <T> List<T> getList(String key, Class<T> value) {
        List list = new ArrayList();
        if (contains(key)) {
            String array = sharedPreferences.getString(key, "");
            Type type = new ListParameterizedType(value);
            list = new Gson().fromJson(array, type);
        }
        return list;
    }

    /**
     * Mark in the editor that a preference value should be removed, which
     * will be done in the actual preferences once you called this method
     * called.
     * <p/>
     * <p>Note that when committing back to the preferences, all removals
     * are done first, regardless of whether you called remove before
     * or after put methods on this editor.
     *
     * @param key The name of the preference to remove.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public void remove(String key) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the
     * preferences.  Once commit is called, the only remaining preferences
     * will be any that you have defined in this editor.
     * <p/>
     * <p>Note that when committing back to the preferences, the clear
     * is done first, regardless of whether you called clear before
     * or after put methods on this editor.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public void clear() {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Registers a callback to be invoked when a change happens to a preference.
     * <p/>
     * <p class="caution"><strong>Caution:</strong> The preference manager does
     * not currently store a strong reference to the listener. You must store a
     * strong reference to the listener, or it will be susceptible to garbage
     * collection. We recommend you keep a reference to the listener in the
     * instance data of an object that will exist as long as you need the
     * listener.</p>
     *
     * @param listener The callback that will run.
     * @see #unregisterOnSharedPreferenceChangeListener
     */
    public void registerOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Unregisters a previous callback.
     *
     * @param listener The callback that should be unregistered.
     * @see #registerOnSharedPreferenceChangeListener
     */
    public void unregisterOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }

}
