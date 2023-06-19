package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.config.assembler.BookModelAssembler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.hateoas.CollectionModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class BookJsonTest {
    @Autowired
    private JacksonTester<BookDto> json;
    @Autowired
    private JacksonTester<CollectionModel<BookDto>> jsonList;
    private final BookModelAssembler bookModelAssembler = new BookModelAssembler();
    private final BookDto[] books = createBookDtoArray(3);
    private final File jsonSingleFile = new File("src/test/resources/example/bookDto.json");
    private final File jsonListFile = new File("src/test/resources/example/bookDtoList.json");

    @Test
    @SneakyThrows
    void BookDtoSerializationTest() {
        BookDto entityModel = bookModelAssembler.toModel(BookDtoMapper.map(books[0]));
        String jsonFromFile = getJsonStringWithLinksFromFile(jsonSingleFile);
        String jsonFromObject = getJsonStringWithLinksFromBookDto(books[0]);

        assertThat(jsonFromObject).isEqualTo(jsonFromFile);
        assertThatJsonObjectHasAllFieldsCorrect(entityModel);
    }

    @Test
    @SneakyThrows
    void BookDtoDeserializationTest() {
        BookDto object = json.parse(getJsonStringWithLinksFromFile(jsonSingleFile)).getObject();
        BookDto entityModel = bookModelAssembler.toModel(BookDtoMapper.map(object));

        String jsonFromString = getJsonStringWithLinksFromBookDto(object);
        String jsonFromObject = getJsonStringWithLinksFromBookDto(books[0]);

        assertThat(jsonFromString).isEqualTo(jsonFromObject);
        assertThatJsonObjectHasAllFieldsCorrect(entityModel);
    }

    @Test
    @SneakyThrows
    void BookDtoListSerializationTest() {
        String jsonStringListFromFile = getJsonStringWithLinksListFromFile(jsonListFile);
        String jsonStringListFromBookDto = getJsonStringWithLinksListFromBookDto(books);
        assertThat(jsonStringListFromFile).isEqualTo(jsonStringListFromBookDto);
    }

    private void assertThatJsonObjectHasAllFieldsCorrect(BookDto entityModel) throws IOException {
        assertThat(json.write(entityModel)).hasJsonPathNumberValue("$.id");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.title");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.author");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.publisher");
        assertThat(json.write(entityModel)).hasJsonPathNumberValue("$.release_year");
        assertThat(json.write(entityModel)).hasJsonPathNumberValue("$.pages");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.isbn");
        assertThat(json.write(entityModel)).hasJsonPathBooleanValue("$.availability");
        assertThat(json.write(entityModel)).hasJsonPathArrayValue("$.links");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.links[0].rel");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.links[0].href");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.links[1].rel");
        assertThat(json.write(entityModel)).hasJsonPathStringValue("$.links[1].href");

        assertThat(json.write(entityModel)).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.title").isEqualTo("Test Title 1");
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.author").isEqualTo("Test Author 1");
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.publisher").isEqualTo("Test Publisher 1");
        assertThat(json.write(entityModel)).extractingJsonPathNumberValue("$.release_year").isEqualTo(2023);
        assertThat(json.write(entityModel)).extractingJsonPathNumberValue("$.pages").isEqualTo(543);
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.isbn").isEqualTo("Test ISBN 1");
        assertThat(json.write(entityModel)).extractingJsonPathBooleanValue("$.availability").isEqualTo(true);
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.links[0].rel").isEqualTo("self");
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.links[0].href").isEqualTo("/api/v1/books/1");
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.links[1].rel").isEqualTo("collection");
        assertThat(json.write(entityModel)).extractingJsonPathStringValue("$.links[1].href").isEqualTo("/api/v1/books");
    }

    private String getJsonStringWithLinksFromBookDto(BookDto book) throws IOException {
        return json.write(bookModelAssembler.toModel(BookDtoMapper.map(book))).getJson();
    }

    private String getJsonStringWithLinksListFromBookDto(BookDto[] bookDtos) throws IOException {
        List<Book> books = Arrays.stream(bookDtos)
                .map(BookDtoMapper::map)
                .toList();
        return jsonList.write(bookModelAssembler.toCollectionModel(books)).getJson();
    }

    private String getJsonStringWithLinksFromFile(File file) throws IOException {
        BookDto bookFromFile = json.read(file).getObject();
        BookDto bookDtoEntityModel = bookModelAssembler.toModel(BookDtoMapper.map(bookFromFile));
        return json.write(bookDtoEntityModel).getJson();
    }

    private String getJsonStringWithLinksListFromFile(File file) throws IOException {
        StringBuilder jsonWithoutSpaces = getJsonWithoutSpaces(file);
        String[] split = extractObjectsFromJsonWithoutLinks(jsonWithoutSpaces);
        BookDto[] booksDto = createDtoObjects(split);
        return getJsonStringWithLinksListFromBookDto(booksDto);
    }

    private BookDto[] createDtoObjects(String[] split) throws IOException {
        BookDto[] b = new BookDto[split.length];
        for (int i = 0; i < split.length; i++) {
            String jsonStr = "{" + split[i] + "}";
            b[i] = json.parse(jsonStr).getObject();
        }
        return b;
    }

    private static String[] extractObjectsFromJsonWithoutLinks(StringBuilder jsonWithoutSpaces) {
        jsonWithoutSpaces.delete(0, 31);
        jsonWithoutSpaces.delete(jsonWithoutSpaces.length() - 69, jsonWithoutSpaces.length()-2);
        String s = jsonWithoutSpaces.toString()
                .replaceAll(",\"_links\"\\s*:\\s*\\{.+?\\},\"collection\"\\s*:\\s*\\{.+?\\}\\}", "");
        return s.substring(1, s.length() - 4).split("\\}\\s*,\\s*\\{");
    }

    private StringBuilder getJsonWithoutSpaces(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder jsonWithoutSpaces = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            String lineWithoutSpaces = line.trim();
            jsonWithoutSpaces.append(lineWithoutSpaces);
        }
        return jsonWithoutSpaces;
    }

    private BookDto[] createBookDtoArray(int amountOfElements) {
        BookDto[] books = new BookDto[amountOfElements];
        for (int i = 0; i < amountOfElements; i++) {
            books[i] = createBookDto(i + 1);
        }
        return books;
    }

    private BookDto createBookDto(long id) {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle("Test Title " + id);
        bookDto.setAuthor("Test Author " + id);
        bookDto.setPublisher("Test Publisher " + id);
        bookDto.setRelease_year(2023);
        bookDto.setPages(543);
        bookDto.setIsbn("Test ISBN " + id);
        bookDto.setAvailability(true);
        return bookDto;
    }
}
