package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.NativeContext;
import io.realm.internal.OsList;
import io.realm.internal.OsMap;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSet;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.core.NativeRealmAny;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_gtn_TimestampRealmProxy extends com.example.gtn.Timestamp
    implements RealmObjectProxy, com_example_gtn_TimestampRealmProxyInterface {

    static final class TimestampColumnInfo extends ColumnInfo {
        long dateColKey;

        TimestampColumnInfo(OsSchemaInfo schemaInfo) {
            super(1);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Timestamp");
            this.dateColKey = addColumnDetails("date", "date", objectSchemaInfo);
        }

        TimestampColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new TimestampColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final TimestampColumnInfo src = (TimestampColumnInfo) rawSrc;
            final TimestampColumnInfo dst = (TimestampColumnInfo) rawDst;
            dst.dateColKey = src.dateColKey;
        }
    }

    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private TimestampColumnInfo columnInfo;
    private ProxyState<com.example.gtn.Timestamp> proxyState;

    com_example_gtn_TimestampRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (TimestampColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.gtn.Timestamp>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dateColKey);
    }

    @Override
    public void realmSet$date(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'date' cannot be changed after object was created.");
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(NO_ALIAS, "Timestamp", false, 1, 0);
        builder.addPersistedProperty(NO_ALIAS, "date", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static TimestampColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new TimestampColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Timestamp";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Timestamp";
    }

    @SuppressWarnings("cast")
    public static com.example.gtn.Timestamp createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.gtn.Timestamp obj = null;
        if (update) {
            Table table = realm.getTable(com.example.gtn.Timestamp.class);
            TimestampColumnInfo columnInfo = (TimestampColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class);
            long pkColumnKey = columnInfo.dateColKey;
            long objKey = Table.NO_MATCH;
            if (!json.isNull("date")) {
                objKey = table.findFirstString(pkColumnKey, json.getString("date"));
            }
            if (objKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_gtn_TimestampRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("date")) {
                if (json.isNull("date")) {
                    obj = (io.realm.com_example_gtn_TimestampRealmProxy) realm.createObjectInternal(com.example.gtn.Timestamp.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_gtn_TimestampRealmProxy) realm.createObjectInternal(com.example.gtn.Timestamp.class, json.getString("date"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'date'.");
            }
        }

        final com_example_gtn_TimestampRealmProxyInterface objProxy = (com_example_gtn_TimestampRealmProxyInterface) obj;
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.gtn.Timestamp createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.gtn.Timestamp obj = new com.example.gtn.Timestamp();
        final com_example_gtn_TimestampRealmProxyInterface objProxy = (com_example_gtn_TimestampRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$date(null);
                }
                jsonHasPrimaryKey = true;
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'date'.");
        }
        return realm.copyToRealmOrUpdate(obj);
    }

    static com_example_gtn_TimestampRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class), false, Collections.<String>emptyList());
        io.realm.com_example_gtn_TimestampRealmProxy obj = new io.realm.com_example_gtn_TimestampRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.gtn.Timestamp copyOrUpdate(Realm realm, TimestampColumnInfo columnInfo, com.example.gtn.Timestamp object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.gtn.Timestamp) cachedRealmObject;
        }

        com.example.gtn.Timestamp realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.gtn.Timestamp.class);
            long pkColumnKey = columnInfo.dateColKey;
            long objKey = table.findFirstString(pkColumnKey, ((com_example_gtn_TimestampRealmProxyInterface) object).realmGet$date());
            if (objKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_gtn_TimestampRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.gtn.Timestamp copy(Realm realm, TimestampColumnInfo columnInfo, com.example.gtn.Timestamp newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.gtn.Timestamp) cachedRealmObject;
        }

        com_example_gtn_TimestampRealmProxyInterface unmanagedSource = (com_example_gtn_TimestampRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.gtn.Timestamp.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.dateColKey, unmanagedSource.realmGet$date());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_gtn_TimestampRealmProxy managedCopy = newProxyInstance(realm, row);
        cache.put(newObject, managedCopy);

        return managedCopy;
    }

    public static long insert(Realm realm, com.example.gtn.Timestamp object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.gtn.Timestamp.class);
        long tableNativePtr = table.getNativePtr();
        TimestampColumnInfo columnInfo = (TimestampColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class);
        long pkColumnKey = columnInfo.dateColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_gtn_TimestampRealmProxyInterface) object).realmGet$date();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, objKey);
        return objKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gtn.Timestamp.class);
        long tableNativePtr = table.getNativePtr();
        TimestampColumnInfo columnInfo = (TimestampColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class);
        long pkColumnKey = columnInfo.dateColKey;
        com.example.gtn.Timestamp object = null;
        while (objects.hasNext()) {
            object = (com.example.gtn.Timestamp) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_gtn_TimestampRealmProxyInterface) object).realmGet$date();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, objKey);
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.gtn.Timestamp object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.gtn.Timestamp.class);
        long tableNativePtr = table.getNativePtr();
        TimestampColumnInfo columnInfo = (TimestampColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class);
        long pkColumnKey = columnInfo.dateColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_gtn_TimestampRealmProxyInterface) object).realmGet$date();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, objKey);
        return objKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gtn.Timestamp.class);
        long tableNativePtr = table.getNativePtr();
        TimestampColumnInfo columnInfo = (TimestampColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.Timestamp.class);
        long pkColumnKey = columnInfo.dateColKey;
        com.example.gtn.Timestamp object = null;
        while (objects.hasNext()) {
            object = (com.example.gtn.Timestamp) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_gtn_TimestampRealmProxyInterface) object).realmGet$date();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, objKey);
        }
    }

    public static com.example.gtn.Timestamp createDetachedCopy(com.example.gtn.Timestamp realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.gtn.Timestamp unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.gtn.Timestamp();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.gtn.Timestamp) cachedObject.object;
            }
            unmanagedObject = (com.example.gtn.Timestamp) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_gtn_TimestampRealmProxyInterface unmanagedCopy = (com_example_gtn_TimestampRealmProxyInterface) unmanagedObject;
        com_example_gtn_TimestampRealmProxyInterface realmSource = (com_example_gtn_TimestampRealmProxyInterface) realmObject;
        Realm objectRealm = (Realm) ((RealmObjectProxy) realmObject).realmGet$proxyState().getRealm$realm();
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());

        return unmanagedObject;
    }

    static com.example.gtn.Timestamp update(Realm realm, TimestampColumnInfo columnInfo, com.example.gtn.Timestamp realmObject, com.example.gtn.Timestamp newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_example_gtn_TimestampRealmProxyInterface realmObjectTarget = (com_example_gtn_TimestampRealmProxyInterface) realmObject;
        com_example_gtn_TimestampRealmProxyInterface realmObjectSource = (com_example_gtn_TimestampRealmProxyInterface) newObject;
        Table table = realm.getTable(com.example.gtn.Timestamp.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.dateColKey, realmObjectSource.realmGet$date());

        builder.updateExistingTopLevelObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Timestamp = proxy[");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long objKey = proxyState.getRow$realm().getObjectKey();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (objKey ^ (objKey >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_gtn_TimestampRealmProxy aTimestamp = (com_example_gtn_TimestampRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aTimestamp.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aTimestamp.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aTimestamp.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
