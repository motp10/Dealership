package dealership.presentation.models;

import java.util.Set;
import java.util.UUID;

public class DetailOperationRequest {
    public String type;
    public String information;
    public double cost;
    public Set<String> compatitiveModels;
    public UUID id;
}
