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
public class com_example_gtn_ImageDBRealmProxy extends com.example.gtn.ImageDB
    implements RealmObjectProxy, com_example_gtn_ImageDBRealmProxyInterface {

    static final class ImageDBColumnInfo extends ColumnInfo {
        long imageNameColKey;
        long imageTypeColKey;
        long personalDataColKey;

        ImageDBColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("ImageDB");
            this.imageNameColKey = addColumnDetails("imageName", "imageName", objectSchemaInfo);
            this.imageTypeColKey = addColumnDetails("imageType", "imageType", objectSchemaInfo);
            this.personalDataColKey = addColumnDetails("personalData", "personalData", objectSchemaInfo);
        }

        ImageDBColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ImageDBColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ImageDBColumnInfo src = (ImageDBColumnInfo) rawSrc;
            final ImageDBColumnInfo dst = (ImageDBColumnInfo) rawDst;
            dst.imageNameColKey = src.imageNameColKey;
            dst.imageTypeColKey = src.imageTypeColKey;
            dst.personalDataColKey = src.personalDataColKey;
        }
    }

    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private ImageDBColumnInfo columnInfo;
    private ProxyState<com.example.gtn.ImageDB> proxyState;

    com_example_gtn_ImageDBRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ImageDBColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.gtn.ImageDB>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$imageName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageNameColKey);
    }

    @Override
    public void realmSet$imageName(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'imageName' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$imageType() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageTypeColKey);
    }

    @Override
    public void realmSet$imageType(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'imageType' to null.");
            }
            row.getTable().setString(columnInfo.imageTypeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'imageType' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.imageTypeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$personalData() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.personalDataColKey);
    }

    @Override
    public void realmSet$personalData(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'personalData' to null.");
            }
            row.getTable().setString(columnInfo.personalDataColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'personalData' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.personalDataColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(NO_ALIAS, "ImageDB", false, 3, 0);
        builder.addPersistedProperty(NO_ALIAS, "imageName", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "imageType", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "personalData", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ImageDBColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ImageDBColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "ImageDB";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "ImageDB";
    }

    @SuppressWarnings("cast")
    public static com.example.gtn.ImageDB createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.gtn.ImageDB obj = null;
        if (update) {
            Table table = realm.getTable(com.example.gtn.ImageDB.class);
            ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
            long pkColumnKey = columnInfo.imageNameColKey;
            long objKey = Table.NO_MATCH;
            if (!json.isNull("imageName")) {
                objKey = table.findFirstString(pkColumnKey, json.getString("imageName"));
            }
            if (objKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_gtn_ImageDBRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("imageName")) {
                if (json.isNull("imageName")) {
                    obj = (io.realm.com_example_gtn_ImageDBRealmProxy) realm.createObjectInternal(com.example.gtn.ImageDB.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_gtn_ImageDBRealmProxy) realm.createObjectInternal(com.example.gtn.ImageDB.class, json.getString("imageName"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'imageName'.");
            }
        }

        final com_example_gtn_ImageDBRealmProxyInterface objProxy = (com_example_gtn_ImageDBRealmProxyInterface) obj;
        if (json.has("imageType")) {
            if (json.isNull("imageType")) {
                objProxy.realmSet$imageType(null);
            } else {
                objProxy.realmSet$imageType((String) json.getString("imageType"));
            }
        }
        if (json.has("personalData")) {
            if (json.isNull("personalData")) {
                objProxy.realmSet$personalData(null);
            } else {
                objProxy.realmSet$personalData((String) json.getString("personalData"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.gtn.ImageDB createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.gtn.ImageDB obj = new com.example.gtn.ImageDB();
        final com_example_gtn_ImageDBRealmProxyInterface objProxy = (com_example_gtn_ImageDBRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("imageName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imageName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imageName(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("imageType")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imageType((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imageType(null);
                }
            } else if (name.equals("personalData")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$personalData((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$personalData(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'imageName'.");
        }
        return realm.copyToRealmOrUpdate(obj);
    }

    static com_example_gtn_ImageDBRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class), false, Collections.<String>emptyList());
        io.realm.com_example_gtn_ImageDBRealmProxy obj = new io.realm.com_example_gtn_ImageDBRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.gtn.ImageDB copyOrUpdate(Realm realm, ImageDBColumnInfo columnInfo, com.example.gtn.ImageDB object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.example.gtn.ImageDB) cachedRealmObject;
        }

        com.example.gtn.ImageDB realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.gtn.ImageDB.class);
            long pkColumnKey = columnInfo.imageNameColKey;
            long objKey = table.findFirstString(pkColumnKey, ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageName());
            if (objKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_gtn_ImageDBRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.gtn.ImageDB copy(Realm realm, ImageDBColumnInfo columnInfo, com.example.gtn.ImageDB newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.gtn.ImageDB) cachedRealmObject;
        }

        com_example_gtn_ImageDBRealmProxyInterface unmanagedSource = (com_example_gtn_ImageDBRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.imageNameColKey, unmanagedSource.realmGet$imageName());
        builder.addString(columnInfo.imageTypeColKey, unmanagedSource.realmGet$imageType());
        builder.addString(columnInfo.personalDataColKey, unmanagedSource.realmGet$personalData());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_gtn_ImageDBRealmProxy managedCopy = newProxyInstance(realm, row);
        cache.put(newObject, managedCopy);

        return managedCopy;
    }

    public static long insert(Realm realm, com.example.gtn.ImageDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.imageNameColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageName();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, objKey);
        String realmGet$imageType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageType();
        if (realmGet$imageType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageTypeColKey, objKey, realmGet$imageType, false);
        }
        String realmGet$personalData = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalData();
        if (realmGet$personalData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.personalDataColKey, objKey, realmGet$personalData, false);
        }
        return objKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.imageNameColKey;
        com.example.gtn.ImageDB object = null;
        while (objects.hasNext()) {
            object = (com.example.gtn.ImageDB) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageName();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, objKey);
            String realmGet$imageType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageType();
            if (realmGet$imageType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageTypeColKey, objKey, realmGet$imageType, false);
            }
            String realmGet$personalData = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalData();
            if (realmGet$personalData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.personalDataColKey, objKey, realmGet$personalData, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.gtn.ImageDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.imageNameColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageName();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, objKey);
        String realmGet$imageType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageType();
        if (realmGet$imageType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageTypeColKey, objKey, realmGet$imageType, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageTypeColKey, objKey, false);
        }
        String realmGet$personalData = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalData();
        if (realmGet$personalData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.personalDataColKey, objKey, realmGet$personalData, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.personalDataColKey, objKey, false);
        }
        return objKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.imageNameColKey;
        com.example.gtn.ImageDB object = null;
        while (objects.hasNext()) {
            object = (com.example.gtn.ImageDB) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageName();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, objKey);
            String realmGet$imageType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imageType();
            if (realmGet$imageType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageTypeColKey, objKey, realmGet$imageType, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageTypeColKey, objKey, false);
            }
            String realmGet$personalData = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalData();
            if (realmGet$personalData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.personalDataColKey, objKey, realmGet$personalData, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.personalDataColKey, objKey, false);
            }
        }
    }

    public static com.example.gtn.ImageDB createDetachedCopy(com.example.gtn.ImageDB realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.gtn.ImageDB unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.gtn.ImageDB();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.gtn.ImageDB) cachedObject.object;
            }
            unmanagedObject = (com.example.gtn.ImageDB) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_gtn_ImageDBRealmProxyInterface unmanagedCopy = (com_example_gtn_ImageDBRealmProxyInterface) unmanagedObject;
        com_example_gtn_ImageDBRealmProxyInterface realmSource = (com_example_gtn_ImageDBRealmProxyInterface) realmObject;
        Realm objectRealm = (Realm) ((RealmObjectProxy) realmObject).realmGet$proxyState().getRealm$realm();
        unmanagedCopy.realmSet$imageName(realmSource.realmGet$imageName());
        unmanagedCopy.realmSet$imageType(realmSource.realmGet$imageType());
        unmanagedCopy.realmSet$personalData(realmSource.realmGet$personalData());

        return unmanagedObject;
    }

    static com.example.gtn.ImageDB update(Realm realm, ImageDBColumnInfo columnInfo, com.example.gtn.ImageDB realmObject, com.example.gtn.ImageDB newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_example_gtn_ImageDBRealmProxyInterface realmObjectTarget = (com_example_gtn_ImageDBRealmProxyInterface) realmObject;
        com_example_gtn_ImageDBRealmProxyInterface realmObjectSource = (com_example_gtn_ImageDBRealmProxyInterface) newObject;
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.imageNameColKey, realmObjectSource.realmGet$imageName());
        builder.addString(columnInfo.imageTypeColKey, realmObjectSource.realmGet$imageType());
        builder.addString(columnInfo.personalDataColKey, realmObjectSource.realmGet$personalData());

        builder.updateExistingTopLevelObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ImageDB = proxy[");
        stringBuilder.append("{imageName:");
        stringBuilder.append(realmGet$imageName());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imageType:");
        stringBuilder.append(realmGet$imageType());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{personalData:");
        stringBuilder.append(realmGet$personalData());
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
        com_example_gtn_ImageDBRealmProxy aImageDB = (com_example_gtn_ImageDBRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aImageDB.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aImageDB.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aImageDB.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
