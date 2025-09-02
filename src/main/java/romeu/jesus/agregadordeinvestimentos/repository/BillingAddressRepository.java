package romeu.jesus.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import romeu.jesus.agregadordeinvestimentos.entity.BillingAddress;
import romeu.jesus.agregadordeinvestimentos.entity.BillingAddress;
import romeu.jesus.agregadordeinvestimentos.entity.User;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
