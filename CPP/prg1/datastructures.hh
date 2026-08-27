// Datastructures.hh
//
// Student name: Sakari Ollikainen
// Student email: sakari.ollikainen@tuni.fi
// Student number: H300314

#ifndef DATASTRUCTURES_HH
#define DATASTRUCTURES_HH

#include <string>
#include <vector>
#include <tuple>
#include <utility>
#include <limits>
#include <functional>
#include <exception>
#include <memory>
#include <map>

// Types for IDs
using AffiliationID = std::string;
using PublicationID = unsigned long long int;
using Name = std::string;
using Year = unsigned short int;
using Weight = int;
using Distance = int;

// Return values for cases where required thing was not found
AffiliationID const NO_AFFILIATION = "---";
PublicationID const NO_PUBLICATION = -1;
Name const NO_NAME = "!NO_NAME!";
Year const NO_YEAR = -1;
Weight const NO_WEIGHT = -1;

// Return value for cases where integer values were not found
int const NO_VALUE = std::numeric_limits<int>::min();

// Type for a coordinate (x, y)
struct Coord
{
    int x = NO_VALUE;
    int y = NO_VALUE;
};


// Example: Defining == and hash function for Coord so that it can be used
// as key for std::unordered_map/set, if needed
inline bool operator==(Coord c1, Coord c2) { return c1.x == c2.x && c1.y == c2.y; }
inline bool operator!=(Coord c1, Coord c2) { return !(c1==c2); } // Not strictly necessary

struct CoordHash
{
    std::size_t operator()(Coord xy) const
    {
        auto hasher = std::hash<int>();
        auto xhash = hasher(xy.x);
        auto yhash = hasher(xy.y);
        // Combine hash values (magic!)
        return xhash ^ (yhash + 0x9e3779b9 + (xhash << 6) + (xhash >> 2));
    }
};

// Example: Defining < for Coord so that it can be used
// as key for std::map/set
inline bool operator<(Coord c1, Coord c2)
{
    if (c1.y < c2.y) { return true; }
    else if (c2.y < c1.y) { return false; }
    else { return c1.x < c2.x; }
}

// Return value for cases where coordinates were not found
Coord const NO_COORD = {NO_VALUE, NO_VALUE};

// Return value for cases where Distance is unknown
Distance const NO_DISTANCE = NO_VALUE;

// This exception class is there just so that the user interface can notify
// about operations which are not (yet) implemented
class NotImplemented : public std::exception
{
public:
    NotImplemented() : msg_{} {}
    explicit NotImplemented(std::string const& msg) : msg_{msg + " not implemented"} {}

    virtual const char* what() const noexcept override
    {
        return msg_.c_str();
    }
private:
    std::string msg_;
};

// This is the class you are supposed to implement

class Datastructures
{
public:
    Datastructures();
    ~Datastructures();

    // Estimate of performance: O(1)
    // Short rationale for estimate: Funktio tarkastaa
    // mapin koon.
    unsigned int get_affiliation_count();

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää clear methodia
    // jonka tehokkuus on O(n)
    void clear_all();

    // Estimate of performance: O(n)
    // Short rationale for estimate: palauttaa ja kopioi vektorin
    // muistista
    std::vector<AffiliationID> get_all_affiliations();

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksess O(n)
    // Muut toiminnot O(1).
    bool add_affiliation(AffiliationID id, Name const& name, Coord xy);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksess O(n)
    // Muut toiminnot O(1).
    Name get_affiliation_name(AffiliationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksess O(n)
    // Muut toiminnot O(1).
    Coord get_affiliation_coord(AffiliationID id);


    // We recommend you implement the operations below only after implementing the ones above

    // Estimate of performance:O(n)
    // Short rationale for estimate: kopioi mapin key arvot vektoriin ja palauttaa.
    std::vector<AffiliationID> get_affiliations_alphabetically();

    // Estimate of performance:O(n*log(n))
    // Short rationale for estimate: dominoiva asia fuktiossa on prority quien tyhjennys joka on nlog(n)
    std::vector<AffiliationID> get_affiliations_distance_increasing();

    // Estimate of performance: O(1)
    // Short rationale for estimate: käyttää coordia avaimena mapissa ja löytää id:n sillä nopeasti
    AffiliationID find_affiliation_with_coord(Coord xy);

    // Estimate of performance: O(n)
    // Short rationale for estimate: pahimmassa tapauksessa find O(n) mutta muuten aika nopea.
    bool change_affiliation_coord(AffiliationID id, Coord newcoord);


    // We recommend you implement the operations below only after implementing the ones above

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksess O(n)
    // Muut toiminnot O(1).
    bool add_publication(PublicationID id, Name const& name, Year year, const std::vector<AffiliationID> & affiliations);

    // Estimate of performance: O(n)
    // Short rationale for estimate: palauttaa ja kopioi vektorin
    // muistista
    std::vector<PublicationID> all_publications();

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    Name get_publication_name(PublicationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    Year get_publication_year(PublicationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    std::vector<AffiliationID> get_affiliations(PublicationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    bool add_reference(PublicationID id, PublicationID parentid);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    std::vector<PublicationID> get_direct_references(PublicationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    bool add_affiliation_to_publication(AffiliationID affiliationid, PublicationID publicationid);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    std::vector<PublicationID> get_publications(AffiliationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käyttää unordered_map.find joka on pahimmassa tapauksessa O(n)
    // Muut toiminnot O(1).
    PublicationID get_parent(PublicationID id);

    // Estimate of performance:O(n*log(n))
    // Short rationale for estimate: käytetään std::sort ja find peräkkäin
    std::vector<std::pair<Year, PublicationID>> get_publications_after(AffiliationID affiliationid, Year year);

    // Estimate of performance: O(nlog(n))
    // Short rationale for estimate: rekusiivinen funktio joka on puun
    // optimoinin takia O(nlog(n))
    std::vector<PublicationID> get_referenced_by_chain(PublicationID id);


    // Non-compulsory operations

    // Estimate of performance: O(m * k)
    // Short rationale for estimate: Tässä m on publikaatioiden määrä ja n on uniikkien referesien määrä.
    std::vector<PublicationID> get_all_references(PublicationID id);

    // Estimate of performance: O(n)
    // Short rationale for estimate: Käydään for loopilla eri asioita läpi mutta loopi eivät ole sisäkkäin.
    std::vector<AffiliationID> get_affiliations_closest_to(Coord xy);

    // Estimate of performance:O(m * n)
    // Short rationale for estimate: m on publikaatioiden määrä ja n on publikaation affiliaatioiden määrä. Käydään for loopilla läpi niitä.
    bool remove_affiliation(AffiliationID id);

    // Estimate of performance:
    // Short rationale for estimate:
    PublicationID get_closest_common_parent(PublicationID id1, PublicationID id2);

    // Estimate of performance:O(m * k + n * p)
    // Short rationale for estimate: Tässä m on julkaisujen lukumäärä, k on keskimääräinen viittauksien määrä julkaisua kohden,
    //n on affiliaatioiden lukumäärä ja p on keskimääräinen julkaisujen määrä kussakin affiliaatiossa.
    bool remove_publication(PublicationID publicationid);


private:

    struct Affiliation{
        Coord coord;
        Name name;
        std::vector<PublicationID> Pubs_of_aff ={};
    };

    struct Publication{
        PublicationID id;
        Name name;
        Year year;
        PublicationID parent = 0;
        std::vector<AffiliationID> Affs_of_pub ={};
        std::vector<PublicationID> references;
    };

    std::unordered_map<AffiliationID,Affiliation> affiliations;
    std::unordered_map<Coord, AffiliationID, CoordHash> invertedAffiliations;
    std::map<Name, AffiliationID> affiliations_by_name;
    std::vector<AffiliationID> aff_IDs;
    std::unordered_map<PublicationID,Publication> publications;
    std::vector<PublicationID> pub_IDs;

};

#endif // DATASTRUCTURES_HH
