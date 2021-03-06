package com.Projekat.Dostava.service;

import com.Projekat.Dostava.entity.Korisnik;
import com.Projekat.Dostava.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik login(String korisnickoIme, String lozinka) throws AccountNotFoundException, InvalidAttributeValueException {
        Korisnik korisnik = nadjiKorisnika(korisnickoIme,(List<Korisnik>) ((List<Korisnik>)korisnikRepository.findAll()));

        if(korisnik == null)
            throw new AccountNotFoundException("Korisnik sa tim korisnickim imenom ne postoji!");

        if(!korisnik.getLozinka().equals(lozinka))
            throw new InvalidAttributeValueException("Pogresna lozinka!");

        return korisnik;
    }

    private Korisnik nadjiKorisnika(String korisnickoIme, List<Korisnik> set){
        for(Korisnik k : set){
            if(k.getKorisnicko().equals(korisnickoIme))
                return k;
        }

        return null;
    }
}
