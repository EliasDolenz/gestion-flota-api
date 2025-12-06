package APIGestorDeVehiculos.service;

import java.util.List;
import java.util.Optional;

import APIGestorDeVehiculos.domain.Office;
import APIGestorDeVehiculos.repository.OfficeRepository;

public class OfficeService {
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office saveOffice(Office office) {
        return this.officeRepository.save(office);
    }

    public List<Office> findAllOffice() {
        return this.officeRepository.findAll();
    }

    public Optional<Office> findOfficeById(Long idOffice) {
        return this.officeRepository.findById(idOffice);
    }

    public Boolean deleteById(Long idOffice) {
        if (this.officeRepository.existsById(idOffice)) {
            this.officeRepository.deleteById(idOffice);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
