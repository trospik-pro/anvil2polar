package dev.xhyrom.anvil2polar;

import net.hollowcube.polar.AnvilPolar;
import net.hollowcube.polar.ChunkSelector;
import net.hollowcube.polar.PolarWriter;

import java.nio.file.Files;
import java.nio.file.Path;

public class Anvil2Polar {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar Anvil2Polar.jar <world>");
            return;
        }

        String world = args[0];

        System.out.println("Converting " + world + " to Polar...");

        var polarWorld = AnvilPolar.anvilToPolar(Path.of(world));
        var polarWorldBytes = PolarWriter.write(polarWorld);

        System.out.println("Writing Polar world to " + world + ".polar...");

        Files.write(Path.of(world + ".polar"), polarWorldBytes);
    }
}