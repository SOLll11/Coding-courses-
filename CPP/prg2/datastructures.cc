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
    // Write any initialization you need here
}

Datastructures::~Datastructures()
{
    // Write any cleanup you need here
}

unsigned int Datastructures::get_affiliation_count()
{
    return Affiliations.size();
}

void Datastructures::clear_all()
{
    Affiliations.clear();
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
    if (Affiliations.find(id) != Affiliations.end()) {
        return false;
    } else {
        Affiliations[id] = {xy, name};
        invertedAffiliations[xy] = id;
        affiliations_by_name[name] = id;
        aff_IDs.push_back(id);
        return true;
    }
}

Name Datastructures::get_affiliation_name(AffiliationID id)
{
    auto it = Affiliations.find(id);
    return (it != Affiliations.end()) ? it->second.name : NO_NAME;
}

Coord Datastructures::get_affiliation_coord(AffiliationID id)
{
    auto it = Affiliations.find(id);
    return (it != Affiliations.end()) ? it->second.coord : NO_COORD;
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


    for (const auto& affiliation : Affiliations) {
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
    auto it = Affiliations.find(id);
    if (it == Affiliations.end() || it->second.coord == NO_COORD) {
        return false;
    } else {
        Coord oldcoord = it->second.coord;
        Affiliations[id].coord = newcoord;

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
        newpublication.id = id;
        newpublication.name = name;
        newpublication.year = year;
        newpublication.Affs_of_pub = affiliations;
        publications[id] = newpublication;
        pub_IDs.push_back(id);

        for (size_t i = 0; i < affiliations.size(); ++i) {
            for (size_t j = i + 1; j < affiliations.size(); ++j) {
                AffiliationID aff1 = affiliations[i];
                AffiliationID aff2 = affiliations[j];

                auto it_all = std::find_if(all_connections.begin(), all_connections.end(),
                                            [aff1, aff2](const Connection &conn) {
                                                return (conn.aff1 == aff1 && conn.aff2 == aff2) ||
                                                       (conn.aff1 == aff2 && conn.aff2 == aff1);
                                            });

                if (it_all != all_connections.end()) {
                    it_all->weight += 1;
                } else {
                    all_connections.emplace_back(Connection{aff1, aff2, 1});
                }

                auto &connections1 = Affiliations[aff1].Connections;
                auto &connections2 = Affiliations[aff2].Connections;

                auto update_connection = [](std::map<AffiliationID, Connection> &connections, AffiliationID aff1, AffiliationID aff2) {
                    auto it = connections.find(aff2);

                    if (it != connections.end()) {
                        it->second.weight += 1;
                    } else {
                        connections[aff2] = Connection{aff1, aff2, 1};
                    }
                };

                update_connection(connections1, aff1, aff2);
                update_connection(connections2, aff2, aff1);
            }
        }
        for (const auto& connection : all_connections)
            {
            auto aff11 = connection.aff1;
            auto aff22 = connection.aff2;
                if ((connection.aff1 == aff11 && connection.aff2 == aff22) || (connection.aff1 == aff22 && connection.aff2 == aff11))
                {
                    auto& aff1_connections = Affiliations[aff11].Connections;
                    auto& aff2_connections = Affiliations[aff22].Connections;

                    auto it1 = aff1_connections.find(aff22);
                    auto it2 = aff2_connections.find(aff11);

                    if (it1 != aff1_connections.end() && it2 != aff2_connections.end())
                    {
                        if (it1->second.weight != it2->second.weight)
                        {
                            it1->second.weight = it2->second.weight = std::max(it1->second.weight, it2->second.weight);
                        }
                    }
                }
            }

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
    auto pubIt = publications.find(publicationid);
    auto affIt = Affiliations.find(affiliationid);

    if (pubIt == publications.end() || affIt == Affiliations.end()) {
        return false;
    }

    auto& pub = pubIt->second;
    auto& aff = affIt->second;

    pub.Affs_of_pub.push_back(affiliationid);

    for (AffiliationID existingAffiliation : pub.Affs_of_pub) {
        if (existingAffiliation != affiliationid) {
            bool connectionExists = false;

            auto& existingConnection = aff.Connections[existingAffiliation];

            if (existingConnection.aff1 == affiliationid || existingConnection.aff2 == affiliationid) {
                existingConnection.weight += 1;
                connectionExists = true;
            }

            if (!connectionExists) {
                aff.Connections[existingAffiliation] = Connection{affiliationid, existingAffiliation, 1};
                Affiliations.at(existingAffiliation).Connections[affiliationid] = aff.Connections[existingAffiliation];
                all_connections.push_back(aff.Connections[existingAffiliation]);
            }
        }
    }

    return true;
}

std::vector<PublicationID> Datastructures::get_publications(AffiliationID id)
{
    if (Affiliations.find(id) == Affiliations.end()) {
        std::vector<PublicationID> no_pub = {NO_PUBLICATION};
        return no_pub;
    }else{
        return Affiliations.at(id).Pubs_of_aff;
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
    if (Affiliations.find(affiliationid) == Affiliations.end()) {
        std::vector<std::pair<Year, PublicationID>> no_pub;
        no_pub.push_back(std::make_pair(NO_YEAR, NO_PUBLICATION));
        return no_pub;
    }else{
        std::vector<std::pair<Year, PublicationID>> pub_and_year;
        for (const PublicationID& publicationid : Affiliations.at(affiliationid).Pubs_of_aff){
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


    for (const auto& affiliation : Affiliations) {
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
    auto affiliation_it = Affiliations.find(id);
    if (affiliation_it == Affiliations.end()) {
        return false;
    }

    invertedAffiliations.erase(Affiliations[id].coord);
    affiliations_by_name.erase(Affiliations[id].name);
    Affiliations.erase(affiliation_it);
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

    for (auto& affiliation : Affiliations) {
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

std::vector<Connection> Datastructures::get_connected_affiliations(AffiliationID id)
{
    auto affiliation_it = Affiliations.find(id);
    if (affiliation_it == Affiliations.end()) {
        return {};
    }

    std::vector<Connection> connections;
    for (const auto& connectionEntry : Affiliations.at(id).Connections)
    {
        const Connection& connection = connectionEntry.second;

        if (connection.aff1 == id)
        {
            connections.push_back(connection);
        }
        else if (connection.aff2 == id)
        {
            Connection reversed_connection = {connection.aff2, connection.aff1, connection.weight};
            connections.push_back(reversed_connection);
        }
    }

    std::sort(connections.begin(), connections.end(),
              [](const Connection& a, const Connection& b) {
                  return a.weight > b.weight;
              });

    return connections;
}

std::vector<Connection> Datastructures::get_all_connections()
{
    std::vector<Connection> result;

    std::set<std::pair<AffiliationID, AffiliationID>> seen_connections;

    for (const auto& affiliationEntry : Affiliations)
    {
        for (const auto& connectionEntry : affiliationEntry.second.Connections)
        {
            const Connection& connection = connectionEntry.second;

            std::pair<AffiliationID, AffiliationID> connection_pair(std::min(connection.aff1, connection.aff2),
                                                                     std::max(connection.aff1, connection.aff2));

            if (seen_connections.find(connection_pair) == seen_connections.end())
            {
                result.push_back(connection);
                seen_connections.insert(connection_pair);
            }
        }
    }

    for (auto& connection : result) {
        if (connection.aff1 > connection.aff2) {
            std::swap(connection.aff1, connection.aff2);
        }
    }

    return result;
}


Path Datastructures::get_any_path(AffiliationID source, AffiliationID target)
{
    Path result;

    auto sourceIter = Affiliations.find(source);
    auto targetIter = Affiliations.find(target);

    if (sourceIter == Affiliations.end() || targetIter == Affiliations.end()) {
        return result;
    }

    std::set<AffiliationID> visited;

    std::function<bool(AffiliationID)> dfs = [&](AffiliationID current) -> bool {

        visited.insert(current);

        if (current == target) {
            return true;
        }

        for (const auto& connectionEntry : Affiliations[current].Connections) {
            const Connection& connection = connectionEntry.second;
            AffiliationID nextAffiliation = (connection.aff1 == current) ? connection.aff2 : connection.aff1;

            if (visited.find(nextAffiliation) == visited.end()) {
                if (dfs(nextAffiliation)) {
                    result.push_back(connection);
                    return true;
                }
            }
        }

        return false;
    };

    dfs(source);
    std::reverse(result.begin(), result.end());

    return result;
}



Path Datastructures::get_path_with_least_affiliations(AffiliationID source, AffiliationID target)
{
    std::vector<Connection> result;

    auto sourceIter = Affiliations.find(source);
    auto targetIter = Affiliations.find(target);

    if (sourceIter == Affiliations.end() || targetIter == Affiliations.end()) {
        return result; // Return empty vector if either source or target is not found
    }

    std::unordered_map<AffiliationID, int> distance;
    std::unordered_map<AffiliationID, Connection> previousConnection;
    std::queue<AffiliationID> q;

    for (const auto& affiliation : Affiliations) {
        distance[affiliation.first] = std::numeric_limits<int>::max();
        previousConnection[affiliation.first] = NO_CONNECTION;
    }
    distance[source] = 0;
    q.push(source);

    while (!q.empty()) {
        AffiliationID currentAffiliation = q.front();
        q.pop();

        for (const auto& connectionEntry : Affiliations[currentAffiliation].Connections) {
            const Connection& connection = connectionEntry.second;
            AffiliationID nextAffiliation = connection.aff2;

            if (distance[nextAffiliation] == std::numeric_limits<int>::max()) {
                distance[nextAffiliation] = distance[currentAffiliation] + 1;
                previousConnection[nextAffiliation] = connection;
                q.push(nextAffiliation);
            }
        }
    }

    // Reconstruct the path from target to source
    AffiliationID current = target;
    while (previousConnection.find(current) != previousConnection.end()) {
        Connection conn = previousConnection[current];
        result.push_back(conn);
        current = conn.aff1;
    }

    std::reverse(result.begin(), result.end());

    return result;
}


Path Datastructures::get_path_of_least_friction(AffiliationID /*source*/, AffiliationID /*target*/)
{
    // Replace the line below with your implementation
    throw NotImplemented("get_path_of_least_friction()");
}

PathWithDist Datastructures::get_shortest_path(AffiliationID source, AffiliationID target)
{
    PathWithDist result;

    auto sourceIter = Affiliations.find(source);
    auto targetIter = Affiliations.find(target);

    if (sourceIter == Affiliations.end() || targetIter == Affiliations.end()) {
        return result; // Return empty vector if either source or target is not found
    }

    std::unordered_map<AffiliationID, int> distance; // Distance from source to each affiliation
    std::unordered_map<AffiliationID, Connection> previousConnection; // Previous connection in the shortest path

    // Priority queue to store affiliations based on their distance from the source
    std::priority_queue<std::pair<int, AffiliationID>, std::vector<std::pair<int, AffiliationID>>, std::greater<>> pq;

    // Initialize distances to infinity and source distance to 0
    for (const auto& affiliation : Affiliations) {
        distance[affiliation.first] = std::numeric_limits<int>::max();
        previousConnection[affiliation.first] = NO_CONNECTION;
    }
    distance[source] = 0;

    // Add source to the priority queue
    pq.push({0, source});

    while (!pq.empty()) {
        AffiliationID currentAffiliation = pq.top().second;
        pq.pop();

        // Check if we have reached the target affiliation
        if (currentAffiliation == target) {
            // Reconstruct the path from target to source
            AffiliationID current = target;
           while (previousConnection.find(current) != previousConnection.end()) {
                Connection conn = previousConnection[current];
                int dist = distance[current] - distance[conn.aff1];
                result.push_back({conn, dist});
                current = conn.aff1;
            }

            std::reverse(result.begin(), result.end());
            return result;
        }

        // Update distances and previous connections for neighboring affiliations
        for (const auto& connectionEntry : Affiliations[currentAffiliation].Connections) {
            const Connection& connection = connectionEntry.second;
            AffiliationID nextAffiliation = (connection.aff1 == currentAffiliation) ? connection.aff2 : connection.aff1;

            int newDistance = distance[currentAffiliation] + connection.weight;

            if (newDistance < distance[nextAffiliation]) {
                distance[nextAffiliation] = newDistance;
                previousConnection[nextAffiliation] = connection;
                pq.push({newDistance, nextAffiliation});
            }
        }
    }

    // If we reach here, there is no path from source to target
    return result;
}




