package ru.inspector_files.utils;

public class FileUtils {
    public static String byteCountToDisplay(long bytes) {
        long b = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        return b < 1024L ? bytes + " B"
                : b <= 0xfffccccccccccccL >> 40 ? String.format("%.3f KiB", bytes / 0x1p10)
                : b <= 0xfffccccccccccccL >> 30 ? String.format("%.3f MiB", bytes / 0x1p20)
                : b <= 0xfffccccccccccccL >> 20 ? String.format("%.3f GiB", bytes / 0x1p30)
                : b <= 0xfffccccccccccccL >> 10 ? String.format("%.3f TiB", bytes / 0x1p40)
                : b <= 0xfffccccccccccccL ? String.format("%.3f PiB", (bytes >> 10) / 0x1p40)
                : String.format("%.3f EiB", (bytes >> 20) / 0x1p40);
    }
}
