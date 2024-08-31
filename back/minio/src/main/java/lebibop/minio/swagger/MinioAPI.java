package lebibop.minio.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="Контроллер работы микросервисом фотографий", description="Контроллер работы микросервисом фотографий")
public interface MinioAPI {

    @Operation(summary = "Загрузка фото в хранилище", description = "Загрузка фото в хранилище")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "d437b0e1-28e7-48dd-bfd6-fe82ef209707.jpg")))})
    @PostMapping("/upload")
    ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);


    @Operation(summary = "Выгрузка фото из хранилища", description = "Выгрузка фото из хранилища")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "<Загрузка фото>")))})
    @GetMapping("/download/{filename}")
    ResponseEntity<byte[]> downloadFile(@PathVariable String filename);

    @Operation(summary = "Удаления файла из хранилища", description = "Удаления файла из хранилища")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Фото успешно удалено")))})
    @DeleteMapping("/delete/{filename}")
    ResponseEntity<String> deleteFile(@PathVariable String filename);
}
