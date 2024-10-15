package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 *
 * @author DIGINAMIC
 */
public class RecherchePopulationBorneService extends MenuService {

    @Override
    public void traiter(Recensement rec, Scanner scanner) {

        System.out.println("Quel est le code du département recherché ? ");
        String choix = scanner.nextLine();

        System.out.println("Choississez une population minimum (en milliers d'habitants): ");
        String saisieMin = scanner.nextLine();

        System.out.println("Choississez une population maximum (en milliers d'habitants): ");
        String saisieMax = scanner.nextLine();


        //-> Renvoyez une exception avec un message clair dans tous les cas suivants :

        //o si l’utilisateur saisit une lettre au lieu de chiffres pour le min ?
        if (!NumberUtils.isParsable(saisieMin)) {
            throw new IllegalArgumentException("La population minimum doit être un nombre.");
        }
        // o si l’utilisateur saisit une lettre au lieu de chiffres pour le max ?
        if (!NumberUtils.isParsable(saisieMax)) {
            throw new IllegalArgumentException("La population maximum doit être un nombre.");
        }

        //o si l’utilisateur saisit un min <0 ou un max <0 ou un min > max ?
        int min = Integer.parseInt(saisieMin) * 1000;
        int max = Integer.parseInt(saisieMax) * 1000;

        if (min < 0) {
            throw new IllegalArgumentException("La population minimum ne peut pas être inférieure à 0.");
        }
        if (max < 0) {
            throw new IllegalArgumentException("La population maximum ne peut pas être inférieure à 0.");
        }
        if (min > max) {
            throw new IllegalArgumentException("La population minimum ne peut pas être supérieure à la population maximum.");
        }

//        List<Ville> villes = rec.getVilles();
//        for (Ville ville : villes) {
//            if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
//                if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
//                    System.out.println(ville);
//                }
//            }
//        }

        List<Ville> villes = rec.getVilles();
        boolean validDepartmentCode = villes.stream()
                .anyMatch(ville -> ville.getCodeDepartement().equalsIgnoreCase(choix));

        if (!validDepartmentCode) {
            throw new IllegalArgumentException("Le code département " + choix + " est inconnu.");
        }

        boolean found = false;
        for (Ville ville : villes) {
            if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
                if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
                    System.out.println(ville);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Aucune ville ne correspond à ces critères.");
        }
    }
}