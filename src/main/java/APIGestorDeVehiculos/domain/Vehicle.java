package APIGestorDeVehiculos.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Clase POJO

@Entity
@Table(name = "vehicles")
@Data // Evita tener que utilizar Getters y setters sumado a otros atajos.
@NoArgsConstructor // Evita tener que definir el constructor sin argumentos.
@AllArgsConstructor // Evita tener que definir el constructor con todos los argumentos.
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private Integer currentKm;
    @ManyToOne
    @JoinColumn(name = "asigned_employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;
    private LocalDate dataLastService;
    private LocalDate expirationDateVtv;

    public Long getAssignatedEmployeeId() {
        return this.employee.getId();
    }

}
