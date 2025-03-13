package by.yurhilevich.WebApp.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // Проверяем, есть ли ошибка. Мы сами определим логику обработки.
        HttpStatusCode statusCode = response.getStatusCode();
        return !statusCode.is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();

        // Если код ответа 409 (CONFLICT), логируем, но не выбрасываем исключение
        if (statusCode.value() == HttpStatus.CONFLICT.value()) {
            // Логируем ошибку, но не выбрасываем исключение
            System.out.println("Conflict error: Group already exists.");
        } else {
            // Для других ошибок выбрасываем исключение
            String message = "Unexpected error: " + statusCode;
            throw new IOException(message);
        }
    }
}
