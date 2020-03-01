package sqlUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditRequestTable {
    private String id;
    private String bank_id;
    private String created;
    private String status;
}