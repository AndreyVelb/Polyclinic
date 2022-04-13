package servlet.performer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PerformerDispatcher {

    private final List<Performer> allPerformers;

    public Optional<Performer> definePerformer(HttpServletRequest request){
        for (Performer performer : allPerformers){
            if (performer.isAppropriatePath(request)){
                return Optional.of(performer);
            }
        }
        return Optional.empty();
    }
}
