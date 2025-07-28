/**
 * MIT License
 *
 * Copyright (c) 2020, 2025 Mark Schmieder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.branding;

/**
 * A generalization of the main versioning attributes of software applications,
 * with public data members combined with helper methods that aggregate certain
 * fields for common constructs such as major.minor.revision version numbers.
 */
public class ProductVersion {
    
    /** The owner, manufacturer, or licenser of the product */
    public String productOwner;
    
    /** The base name of the product shared across all product variants */
    public String productBaseName;
    
    /** The level or variant of the product, such as Free or Pro */
    public String productLevel;
    
    /** The protection filed for the product, if any exists, such as (TM) */
    public String protection;
    
    /** The major version of the product release, for major upgrades */
    public int versionMajor;
    
    /** The minor version of the product release, for interim updates */
    public int versionMinor;
    
    /** The revision of the product release, usually for emergency bugfixes */
    public int revision;
    
    /** Additional character to distinguish quick updates; usually a letter */
    public char update;
    
    /** 
     * The Build ID of the product release, which generally gets bumped for 
     * unreleased builds and nightly interim internal releases. The ID is also
     * often useful in client/server situations to determine compatibility.
     */
    public long buildId;
    
    /** 
     * The build date of the product release, not enforced to any format, and
     * generally associated with the time of build vs. the time of release,
     * as there may be regression testing with no code changes before release.
     */
    public String buildDate;
    
    /** 
     * The release date, in case this needs to be queried separately from the
     * build date, especially if there is a large gap in time due to testing.
     * Usually this is the published date and planned with testing in mind.
     */
    public String releaseDate;

    /**
     * Returns an instance of {@code ProductVersion} minimally specified.
     * <p>
     * This constructor leaves out the optional fields and supplies default
     * parameters that effectively remove them from product versioning.
     * <p>
     * Rather than provide every possible combination of under-specified
     * constructors, clients should either mimic these default values in the
     * fully qualified constructor or derive a custom class of their own.
     * 
     * @param pProductBaseName The Product Base Name of the application
     * @param pVersionMajor The major version of the product release
     * @param pVersionMinor The minor version of the product release
     * @param pRevision The revision of the product release
     * @param pReleaseDate The published date of the product release
     */
    public ProductVersion( final String pProductBaseName,
                           final int pVersionMajor,
                           final int pVersionMinor,
                           final int pRevision ,
                           final String pReleaseDate ) {
        this( "",
              pProductBaseName,
              "",
              "",
              pVersionMajor,
              pVersionMinor,
              pRevision,
              0L,
              "",
              pReleaseDate );
    }
    
    /**
     * Returns an instance of {@code ProductVersion} nearly fully specified.
     * 
     * @param pProductOwner The optional owner of the associated application
     * @param pProductBaseName The Product Base Name of the application
     * @param pProductLevel The optional Product Level of the application
     * @param pProtection The optional protection of the application
     * @param pVersionMajor The major version of the product release
     * @param pVersionMinor The minor version of the product release
     * @param pRevision The revision of the product release
     * @param pBuildId The optional Build ID of the baseline for the release
     * @param pBuildDate The optional build date of the code for the release
     * @param pReleaseDate The published date of the product release
     */
    public ProductVersion( final String pProductOwner,
                           final String pProductBaseName,
                           final String pProductLevel,
                           final String pProtection,
                           final int pVersionMajor,
                           final int pVersionMinor,
                           final int pRevision,
                           final long pBuildId,
                           final String pBuildDate,
                           final String pReleaseDate ) {
        this( pProductOwner,
              pProductBaseName,
              pProductLevel,
              pProtection,
              pVersionMajor,
              pVersionMinor,
              pRevision,
              ' ',
              pBuildId,
              pBuildDate,
              pReleaseDate );
    }
    
    /**
     * Returns an instance of {@code ProductVersion} fully specified.
     * <p>
     * This slightly expanded constructor pulls in the new optional field
     * for distinguishing quick builds and patches for a revision, which
     * usually is represented by a single lower-case letter from a to z.
     * 
     * @param pProductOwner The optional owner of the associated application
     * @param pProductBaseName The Product Base Name of the application
     * @param pProductLevel The optional Product Level of the application
     * @param pProtection The optional protection of the application
     * @param pVersionMajor The major version of the product release
     * @param pVersionMinor The minor version of the product release
     * @param pRevision The revision of the product release
     * @param pUpdate The update of the revision, usually a letter (a..z)
     * @param pBuildId The optional Build ID of the baseline for the release
     * @param pBuildDate The optional build date of the code for the release
     * @param pReleaseDate The published date of the product release
     */
    public ProductVersion( final String pProductOwner,
                           final String pProductBaseName,
                           final String pProductLevel,
                           final String pProtection,
                           final int pVersionMajor,
                           final int pVersionMinor,
                           final int pRevision,
                           final char pUpdate,
                           final long pBuildId,
                           final String pBuildDate,
                           final String pReleaseDate ) {
        productOwner = pProductOwner;
        productBaseName = pProductBaseName;
        productLevel = pProductLevel;
        protection = pProtection;
        
        versionMajor = pVersionMajor;
        versionMinor = pVersionMinor;
        revision = pRevision;
        update = pUpdate;
        
        buildId = pBuildId;
        buildDate = pBuildDate;
        releaseDate = pReleaseDate;
    }
    
    /**
     * Returns the full aggregated parts of the product version as a string.
     * 
     * @return the full aggregated parts of the product version as a string
     */
    public String getVersionNumber() {
        final StringBuilder versionNumber = new StringBuilder();
        versionNumber.append( versionMajor );
        versionNumber.append( "." );
        versionNumber.append( versionMinor );
        versionNumber.append( "." );
        versionNumber.append( revision );
        if ( update != ' ' ) {
            versionNumber.append( update );
        }
        return versionNumber.toString().trim();
    }

    public String getProductName() {
        String productName = productBaseName;
        if ( !productLevel.trim().isEmpty() ) {
            productName += " " + productLevel;
        }
        return productName;
    }

    public String getProductProtectedName() {
        String productName = productBaseName;
        if ( !protection.trim().isEmpty() ) {
            productName += " " + protection;
        }
        if ( !productLevel.trim().isEmpty() ) {
            productName += " " + productLevel;
        }
        return productName;
    }

    public String getProductReleaseName() {
        return getProductName() + " " + getVersionNumber();
    }

   public String getProductReleaseProtectedName() {
       return getProductProtectedName() + " " + getVersionNumber();
    }

   public String getFullProductReleaseProtectedName() {
       final String productReleaseProtectedName = getProductReleaseProtectedName();
       return ( !productOwner.trim().isEmpty() )
               ? productOwner + " " + productReleaseProtectedName
               : productReleaseProtectedName;
    }
}
