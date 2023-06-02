package backend.formatter;

import backend.model.Sample;
import backend.services.sample.ISampleService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class SampleFormatter implements Formatter<Sample> {
    private final ISampleService sampleService;

    public SampleFormatter(ISampleService sampleService) {
        this.sampleService = sampleService;
    }

    @Override
    public Sample parse(String text, Locale locale) {
        Optional<Sample> sampleOptional = sampleService.findById(Long.parseLong(text));
        return sampleOptional.orElse(null);
    }

    @Override
    public String print(Sample object, Locale locale) {
        return object.toString();
    }
}
