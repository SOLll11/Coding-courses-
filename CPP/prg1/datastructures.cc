// Datastructures.cc
//
// Student name: Sakari Ollikainen
// Student email: sakari.ollikainen@tuni.fi
// Student number: H300314

#include "datastructures.hh"

#include <random>
#include <queue>
#include <cmath>
#include <algorithm>
#include <set>
#include <unordered_set>




std::minstd_rand rand_engine; // Reasonably quick pseudo-random generator

template <typename Type>
Type random_in_range(Type start, Type end)
{
    auto range = end-start;
    ++range;

    auto num = std::uniform_int_distribution<unsigned long int>(0, range-1)(rand_engine);

    return static_cast<Type>(start+num);
}

// Modify the code below to implement the functionality of the class.
// Also remove comments from the parameter names when you implement
// an operation (Commenting out parameter name prevents compiler from
// warning about unused parameters on operations you haven't yet implemented.)

Datastructures::Datastructures()
{

}

Datastructures::~Datastructures()
{
    // Write any cleanup you need here
}

unsigned int Datastructures::get_affiliation_count()
{
    return affiliations.size();
}

void Datastructures::clear_all()
{
    affiliations.clear();
    invertedAffiliations.clear();
    affiliations_by_name.clear();
    publications.clear();
    aff_IDs.clear();
    pub_IDs.clear();
}

std::vector<AffiliationID> Datastructures::get_all_affiliations()
{ 
    return aff_IDs;
}

bool Datastructures::add_affiliation(AffiliationID id, const Name &name, Coord xy)
{
    if (affiliations.find(id) != affiliations.end()) {
        return false;
    } else {
        affiliations[id] = {xy, name};
        invertedAffiliations[xy] = id;
        affiliations_by_name[name] = id;
        aff_IDs.push_back(id);
        return true;
    }
}

Name Datastructures::get_affiliation_name(AffiliationID id)
{
    auto it = affiliations.find(id);
    return (it != affiliations.end()) ? it->second.name : NO_NAME;
}

Coord Datastructures::get_affiliation_coord(AffiliationID id)
{
    auto it = affiliations.find(id);
    return (it != affiliations.end()) ? it->second.coord : NO_COORD;
}

std::vector<AffiliationID> Datastructures::get_affiliations_alphabetically()
{
    std::vector<AffiliationID> retval;
    retval.reserve(affiliations_by_name.size());

    for (const auto& element : affiliations_by_name) {
        retval.emplace_back(element.second);
    }

    return retval;
}

std::vector<AffiliationID> Datastructures::get_affiliations_distance_increasing()
{
    std::vector<AffiliationID> sortedAffiliations;


    auto longer = [this](const AffiliationID& id1, const AffiliationID& id2) {
        const Coord coord1 = get_affiliation_coord(id1);
        const Coord coord2 = get_affiliation_coord(id2);

        const double distance1 = std::sqrt(std::pow(coord1.x, 2) + std::pow(coord1.y, 2));
        const double distance2 = std::sqrt(std::pow(coord2.x, 2) + std::pow(coord2.y, 2));

        if (distance1 != distance2) {
            return distance1 > distance2;
        } else {
            return coord1.y > coord2.y;
        }
    };

    std::priority_queue<AffiliationID, std::vector<AffiliationID>, decltype(longer)> pq(longer);


    for (const auto& affiliation : affiliations) {
        pq.push(affiliation.first);
    }
    while (!pq.empty()) {
        sortedAffiliations.push_back(pq.top());
        pq.pop();
    }

    return sortedAffiliations;
}


AffiliationID Datastructures::find_affiliation_with_coord(Coord xy)
{
    auto it = invertedAffiliations.find(xy);
    if (it != invertedAffiliations.end()) {
        return it->second;
    }
    return NO_AFFILIATION;
}

bool Datastructures::change_affiliation_coord(AffiliationID id, Coord newcoord)
{
    auto it = affiliations.find(id);
    if (it == affiliations.end() || it->second.coord == NO_COORD) {
        return false;
    } else {
        Coord oldcoord = it->second.coord;
        affiliations[id].coord = newcoord;

        invertedAffiliations.erase(oldcoord);
        invertedAffiliations[newcoord] = id;

        return true;
    }
}

bool Datastructures::add_publication(PublicationID id, const Name &name, Year year, const std::vector<AffiliationID> &affiliations)
{
     if (publications.find(id) != publications.end()) {
        return false;
    } else {
        Publication newpublication;
        newpublication.name = name;
        newpublication.year = year;
        newpublication.Affs_of_pub = affiliations;
        publications[id] = newpublication;
        pub_IDs.push_back(id);
        return true;
    }
}

std::vector<PublicationID> Datastructures::all_publications()
{
    return pub_IDs;
}

Name Datastructures::get_publication_name(PublicationID id)
{
    if (publications.find(id) == publications.end()) {
        return NO_NAME;
    }else{
        return publications.at(id).name;
    }
}

Year Datastructures::get_publication_year(PublicationID id)
{
   if (publications.find(id) == publications.end()) {
        return NO_YEAR;
    }else{
        return publications.at(id).year;
    }
}

std::vector<AffiliationID> Datastructures::get_affiliations(PublicationID id)
{
    if (publications.find(id) == publications.end()) {
        std::vector<AffiliationID>no_aff = {NO_AFFILIATION};
        return no_aff;
    }else{
        return publications.at(id).Affs_of_pub;
    }
}

bool Datastructures::add_reference(PublicationID id, PublicationID parentid)
{
     if (publications.find(id) == publications.end() and
         publications.find(parentid) == publications.end()) {
         return false;
     }else{
         publications.at(parentid).references.push_back(id);
         publications.at(id).parent = parentid;
         return true;
     }
}

std::vector<PublicationID> Datastructures::get_direct_references(PublicationID id)
{
    if (publications.find(id) == publications.end()) {
        std::vector<PublicationID>no_pub = {NO_PUBLICATION};
        return no_pub;
    }else{
        return publications.at(id).references;
    }
}

bool Datastructures::add_affiliation_to_publication(AffiliationID affiliationid, PublicationID publicationid)
{
    if (publications.find(publicationid) == publications.end() or
        affiliations.find(affiliationid) == affiliations.end()) {
        return false;
    }else{
        publications.at(publicationid).Affs_of_pub.push_back(affiliationid);
        affiliations.at(affiliationid).Pubs_of_aff.push_back(publicationid);
        return true;
    }
}

std::vector<PublicationID> Datastructures::get_publications(AffiliationID id)
{
    if (affiliations.find(id) == affiliations.end()) {
        std::vector<PublicationID> no_pub = {NO_PUBLICATION};
        return no_pub;
    }else{
        return affiliations.at(id).Pubs_of_aff;
    }
}

PublicationID Datastructures::get_parent(PublicationID id)
{
    if (publications.find(id) == publications.end()) {
        return NO_PUBLICATION;

    }else if (publications.at(id).parent == 0){
      return NO_PUBLICATION;
    }else{
        return publications.at(id).parent;
    }
}

std::vector<std::pair<Year, PublicationID> > Datastructures::get_publications_after(AffiliationID affiliationid, Year year)
{
    if (affiliations.find(affiliationid) == affiliations.end()) {
        std::vector<std::pair<Year, PublicationID>> no_pub;
        no_pub.push_back(std::make_pair(NO_YEAR, NO_PUBLICATION));
        return no_pub;
    }else{
        std::vector<std::pair<Year, PublicationID>> pub_and_year;
        for (const PublicationID& publicationid : affiliations.at(affiliationid).Pubs_of_aff){
            if (publications.at(publicationid).year >= year) {
               pub_and_year.push_back(std::make_pair(publications.at(publicationid).year,publicationid));
            }
        }
        std::sort(pub_and_year.begin(), pub_and_year.end(), [](const std::pair<Year, PublicationID>& a, const std::pair<Year, PublicationID>& b) {
                    if (a.first != b.first) {
                        return a.first < b.first;
                    }
                    return a.second < b.second;
                });


        return pub_and_year;
    }
}

std::vector<PublicationID> Datastructures::get_referenced_by_chain(PublicationID id)
{
    std::vector<PublicationID> all_parents;

    if (publications.find(id) == publications.end()|| id == 0) {
        return {NO_PUBLICATION};
    } else {

        if (publications.at(id).parent == 0) {
            return {};
        } else {

            if (publications.at(id).parent != 0) {
                all_parents.push_back(publications.at(id).parent);

                std::vector<PublicationID> parent_chain = get_referenced_by_chain(publications.at(id).parent);
                all_parents.insert(all_parents.end(), parent_chain.begin(), parent_chain.end());
            }
        }
    }

    return all_parents;
}

std::vector<PublicationID> Datastructures::get_all_references(PublicationID id)
{
    std::unordered_set<PublicationID> all_references;


    auto it = publications.find(id);
    if (it == publications.end()) {
        return {NO_PUBLICATION};
    }

    std::function<void(PublicationID)> get_references_recursive;
    get_references_recursive = [this, &all_references, &get_references_recursive](PublicationID current_id) {
        const auto& current_publication = publications.find(current_id);
        if (current_publication != publications.end()) {
            for (PublicationID ref_id : current_publication->second.references) {
                if (all_references.emplace(ref_id).second) {
                    get_references_recursive(ref_id);
                }
            }
        }
    };

    get_references_recursive(id);

    return {all_references.begin(), all_references.end()};
}

std::vector<AffiliationID> Datastructures::get_affiliations_closest_to(Coord xy)
{
    std::vector<std::pair<AffiliationID, double>> distances;


    for (const auto& affiliation : affiliations) {
        double distance = std::sqrt(std::pow(affiliation.second.coord.x - xy.x, 2) +
                                    std::pow(affiliation.second.coord.y - xy.y, 2));
        distances.emplace_back(affiliation.first, distance);
    }

    std::sort(distances.begin(), distances.end(), [](const auto& a, const auto& b) {
        return a.second < b.second;
    });

    std::vector<AffiliationID> result;
    for (size_t i = 0; i < std::min(distances.size(), static_cast<size_t>(3)); ++i) {
        result.push_back(distances[i].first);
    }

    return result;
}

bool Datastructures::remove_affiliation(AffiliationID id)
{
    auto affiliation_it = affiliations.find(id);
    if (affiliation_it == affiliations.end()) {
        return false;
    }

    invertedAffiliations.erase(affiliations[id].coord);
    affiliations_by_name.erase(affiliations[id].name);
    affiliations.erase(affiliation_it);
    aff_IDs.erase(std::remove(aff_IDs.begin(), aff_IDs.end(), id), aff_IDs.end());

    for (auto& publication : publications) {
        auto& affs_of_pub = publication.second.Affs_of_pub;
        affs_of_pub.erase(std::remove(affs_of_pub.begin(), affs_of_pub.end(), id), affs_of_pub.end());
    }

    return true;
}

PublicationID Datastructures::get_closest_common_parent(PublicationID /*id1*/, PublicationID /*id2*/)
{
    // Replace the line below with your implementation
    throw NotImplemented("get_closest_common_parent()");

}

bool Datastructures::remove_publication(PublicationID publicationid)
{
    auto publication_it = publications.find(publicationid);
    if (publication_it == publications.end()) {
        return false;
    }

    PublicationID parent_id = publications.at(publicationid).parent;

    publications.erase(publication_it);
    pub_IDs.erase(std::remove(pub_IDs.begin(), pub_IDs.end(), publicationid), pub_IDs.end());

    unsigned long long zero = 0;
    for (auto& publication : publications) {
        auto& references = publication.second.references;

        std::replace(references.begin(), references.end(), publicationid, zero);

        if (publication.second.parent == publicationid) {
            publication.second.parent = 0;
        }

        references.erase(std::remove(references.begin(), references.end(), 0), references.end());
    }

    for (auto& affiliation : affiliations) {
        auto& pubs_of_aff = affiliation.second.Pubs_of_aff;
        pubs_of_aff.erase(std::remove(pubs_of_aff.begin(), pubs_of_aff.end(), publicationid), pubs_of_aff.end());
    }

    if (parent_id != 0) {
        auto parent_it = publications.find(parent_id);
        if (parent_it != publications.end()) {
            auto& parent_references = parent_it->second.references;
            parent_references.erase(std::remove(parent_references.begin(), parent_references.end(), publicationid), parent_references.end());
        }
    }

    return true;
}


