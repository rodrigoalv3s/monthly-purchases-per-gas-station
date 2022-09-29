package br.com.rodrigo.pipeline;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static br.com.rodrigo.pipeline.utils.FileUtils.findFilesStartWith;

@RunWith(JUnit4.class)
public class MonthlyPurchasesPerGasStationTest {
    static final String rootDir = FileSystems.getDefault()
            .getPath("")
                .toAbsolutePath()
                .toString();

    static final String folderPah = rootDir + "/output/";

    @Test
    public void testGcsToBigQueryTest() throws IOException {
        String[] args = {
                String.format("--inputPath=%s/input/", rootDir),
                "--fileName=transactions_1k_202209260946.csv",
                String.format("--outputPath=%s/output/", rootDir)
        };

        MonthlyPurchasesPerGasStation.main(args);

        Assert.assertTrue("Should exist a file with the name prefix output-transactions-summary",
                findFilesStartWith(folderPah, "output-transactions-summary*").size() > 0);
    }

    @After
    public void removeFiles() throws IOException {
        List<String> files = findFilesStartWith(folderPah, "output-transactions-summary*");

        for(String fileName: files) {
            Files.deleteIfExists(Path.of(folderPah + fileName));
        }
    }
}
