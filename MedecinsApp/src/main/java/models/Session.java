/**
*
* Created on 19 juin 2020 by PrinceNar
*/

package models;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class Session {
    private static MedecinUser user;

    public Session(MedecinUser user) {
        Session.user = user;
    }

    public static MedecinUser getUser() {
        return user;
    }

    public static void setUser(MedecinUser user) {
        Session.user = user;
    }
}
