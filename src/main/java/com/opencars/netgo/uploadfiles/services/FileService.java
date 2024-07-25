package com.opencars.netgo.uploadfiles.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    public void init();

    public void save(MultipartFile file, String typeLoad, String id, String interno);

    public void createDirectory(String typeLoad, String id);

    //guardar recibos
    public void createDirectoryPrincipal(String nameDirectory);

    public void change(MultipartFile file, String id, String type);

    public Resource load(String filename, String type, String cuil);

    Stream<Path> loadImgsFromIdOrCUILDirectory(String type, String id);

    Stream<Path> viewPrincipal();

    Stream<Path> viewDirectories(String directory);

    Stream<Path> loadFilesForDirectory(String directory, String file);

    Stream<Path> loadFilesForInternalDirectory(String directory, String subDirectory, String internalDirectory);

    public Stream<Path> loadDocumentsByTicketId(String id);

    public Resource loadFilesByFilename(String filename);

    public String deleteInternalFile(String type, String id, String filename);

    String deleteDirectory(String directory);

    String deleteSubdirectory(String directory, String subdirectory);

    String deleteInternalFileOfSubdirectory(String directory, String subdirectory, String internalfile);

    String deleteInternalFileLastLevel(String directory, String subdirectory, String internalfile, String file);
}
