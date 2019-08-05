package br.gov.inmetro.beacon.v2.mypackage.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.infra.ChainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChainRepository extends JpaRepository<ChainEntity, Integer> {
    ChainEntity findTop1ByActive(boolean active);
}
