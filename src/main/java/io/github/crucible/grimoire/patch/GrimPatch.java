package io.github.crucible.grimoire.patch;

import java.io.File;
import java.util.List;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class GrimPatch implements Comparable<GrimPatch>{

    private long priority;
    private String patchName;
    private String modId;
    private String targetJar;
    private boolean corePatch;
    private List<ZipEntry> mixinEntries;

    public GrimPatch(Manifest manifest, List<ZipEntry> mixinEntries, File modFile) {
        this.priority       = getLong(manifest,"GRIMOIRE_PRIORITY", 0L);
        this.patchName      = getString(manifest,"GRIMOIRE_PATCHNAME", modFile.getName());
        this.modId          = getString(manifest,"GRIMOIRE_MODID", "");
        this.targetJar      = getString(manifest,"GRIMOIRE_TARGETJAR", "");
        this.corePatch      = getBoolean(manifest,"GRIMOIRE_COREPATCH", false);
        this.mixinEntries   = mixinEntries;
    }

    public long getPriority() {
        return priority;
    }

    public String getPatchName() {
        return patchName;
    }

    public String getModId() {
        return modId;
    }

    public String getTargetJar() {
        return targetJar;
    }

    public boolean isCorePatch() {
        return corePatch;
    }

    public List<ZipEntry> getMixinEntries() {
        return mixinEntries;
    }

    private long getLong(Manifest manifest, String key, long def){
        try {
            return Long.parseLong(manifest.getMainAttributes().getValue(key));
        }catch (Exception ignored){
        }
        return def;
    }

    private boolean getBoolean(Manifest manifest, String key, boolean def){
        try {
            return Boolean.parseBoolean(manifest.getMainAttributes().getValue(key));
        }catch (Exception ignored){
        }
        return def;
    }

    private String getString(Manifest manifest, String key, String def){
        String targetValue = manifest.getMainAttributes().getValue(key);
        return targetValue != null ? targetValue : def;
    }

    @Override
    public int compareTo(GrimPatch other) {
        return Long.compare(this.priority,other.priority);
    }
}
