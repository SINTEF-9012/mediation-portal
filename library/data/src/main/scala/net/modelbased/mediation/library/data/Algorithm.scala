package net.modelbased.mediation.library.data



/**
 * Represent a reference to an algorithm exposed as a REST service. It includes
 * a name, a short description, and the URL where the service is available. We
 * assume that all mediation services implement exactly the same interface.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
case class Algorithm(val id: String, val description: String, val url: String)