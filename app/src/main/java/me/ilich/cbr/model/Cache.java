package me.ilich.cbr.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cache {

    private static final String FILE_NAME_VALCURS = "valcurs.cache";

    private static void writeToFile(Context context, String fileName, Object object) {
        File f = context.getFileStreamPath(fileName);
        if (f != null && f.exists()) {
            f.delete();
        }
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static <T> T readFromFile(Context context, String fileName) {
        T result = null;
        File f = context.getFileStreamPath(fileName);
        if (f != null && f.exists()) {
            FileInputStream fis = null;
            ObjectInputStream is = null;
            try {
                fis = context.openFileInput(fileName);
                is = new ObjectInputStream(fis);
                //noinspection unchecked
                result = (T) is.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    private static void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    @Nullable
    private ValCurs valCurs = null;
    private final Context context;

    public Cache(Context context) {
        this.context = context;
    }

    public void replaceValCurs(ValCurs v) {
        valCurs = v;
        writeToFile(context, FILE_NAME_VALCURS, valCurs);
    }

    @Nullable
    public ValCurs getValCurs() {
        final ValCurs result;
        if (valCurs == null) {
            result = readFromFile(context, FILE_NAME_VALCURS);
        } else {
            result = valCurs;
        }
        return result;
    }

    @VisibleForTesting
    public void clear() {
        valCurs = null;
        deleteFile(context, FILE_NAME_VALCURS);
    }

    public boolean containsValCurs() {
        final boolean b;
        if (valCurs != null) {
            b = true;
        } else {
            valCurs = readFromFile(context, FILE_NAME_VALCURS);
            b = valCurs != null;
        }
        return b;
    }

}
